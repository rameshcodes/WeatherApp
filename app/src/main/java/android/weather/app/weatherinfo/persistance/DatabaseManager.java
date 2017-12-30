package android.weather.app.weatherinfo.persistance;


import android.weather.app.weatherinfo.WeatherApplication;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.model.DayWeatherInfo;
import android.weather.app.weatherinfo.model.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class DatabaseManager {
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
                weatherDatabase.getCityDao().insert(city);
            }
        }.start();
    }

    public Observable<List<City>> getFavoriteCities() {
        return Observable.create(new ObservableOnSubscribe<List<City>>() {
            @Override
            public void subscribe(ObservableEmitter<List<City>> e) throws Exception {
                try {
                    List<City> cityList = weatherDatabase.getCityDao().getAllCities();
                    e.onNext(cityList);
                } catch (Throwable t) {
                    e.onError(t);
                }
            }
        });
    }

    public void updateWeatherForecastInfo(Map<Location, Map<String, DayWeatherInfo>> locationForecastDataMap) {
        for (Location location : locationForecastDataMap.keySet()) {
            City city = weatherDatabase.getCityDao().getCityForLatLong(location.getPoint().getLatitude(), location.getPoint().getLongitude()).get(0);
            weatherDatabase.getDayWeatherDao().deleteCityWeatherData(city.getCity());
            insertWeatherData(city, new ArrayList<DayWeatherInfo>(locationForecastDataMap.get(location).values()));
        }
    }
}
