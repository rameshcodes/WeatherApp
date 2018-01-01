package android.weather.app.weatherinfo.persistance;


import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;
import android.weather.app.weatherinfo.WeatherApplication;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.model.DayWeatherInfo;
import android.weather.app.weatherinfo.model.Location;
import android.weather.app.weatherinfo.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class DatabaseManager {
    private static final String TAG = "DatabaseManager";
    private static DatabaseManager databaseManager;

    private WeatherDatabase weatherDatabase;

    public static DatabaseManager getInstance() {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager();
        }
        return databaseManager;
    }

    private DatabaseManager() {
        weatherDatabase = WeatherDatabase.getInstance(WeatherApplication.getContext());
    }

    public void insertWeatherData(City city, List<DayWeatherInfo> dayWeatherInfoList) {
        for (DayWeatherInfo dayWeatherInfo : dayWeatherInfoList) {
            dayWeatherInfo.setCityName(city.getCity());
            weatherDatabase.getDayWeatherDao().insert(dayWeatherInfo);
        }
    }

    public void insertCity(final City city) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    weatherDatabase.getCityDao().insert(city);
                } catch (SQLiteConstraintException e) {
                    Log.e(TAG, "run: " + e.getCause());
                }
            }
        }.start();
    }

    public void deleteCity(final City city) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    weatherDatabase.getCityDao().delete(city);
                } catch (SQLiteConstraintException e) {
                    Log.e(TAG, "run: " + e.getCause());
                }
            }
        }.start();
    }

    public Observable<List<City>> getFavoriteCities() {
        return Observable.create(new ObservableOnSubscribe<List<City>>() {
            @Override
            public void subscribe(ObservableEmitter<List<City>> e) throws Exception {
                try {
                    List<City> cityList = new ArrayList<>();
                    cityList.addAll(weatherDatabase.getCityDao().getAllCities());
                    e.onNext(cityList);
                    e.onComplete();
                } catch (Throwable t) {
                    e.onError(t);
                }
            }
        });
    }

    public void updateWeatherForecastInfo(Map<Location, Map<String, DayWeatherInfo>> locationForecastDataMap) {
        for (Location location : locationForecastDataMap.keySet()) {
            List<City> cities = weatherDatabase.getCityDao().getCityForLatLong(location.getPoint().getLatitude(), location.getPoint().getLongitude());
            if (cities != null && !cities.isEmpty()) {
                City city = cities.get(0);
                weatherDatabase.getDayWeatherDao().deleteCityWeatherData(city.getCity());
                insertWeatherData(city, new ArrayList<DayWeatherInfo>(locationForecastDataMap.get(location).values()));
            }
        }
    }

    public Observable<Map<String, DayWeatherInfo>> getWeatherForecastDataForCity(final City city) {
        return Observable.create(new ObservableOnSubscribe<Map<String, DayWeatherInfo>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, DayWeatherInfo>> e) throws Exception {
                try {
                    List<DayWeatherInfo> dayWeatherInfoList = weatherDatabase.getDayWeatherDao().getWeatherForCity(city.getCity());
                    if (dayWeatherInfoList == null || dayWeatherInfoList.isEmpty()) {
                        e.onError(new Throwable("Data Empty"));
                    }
                    e.onNext(Util.convertWeatherDataToMap(dayWeatherInfoList));
                    e.onComplete();
                } catch (Throwable t) {
                    e.onError(t);
                }
            }
        });
    }
}
