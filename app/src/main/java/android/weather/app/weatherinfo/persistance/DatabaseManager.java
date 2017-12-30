package android.weather.app.weatherinfo.persistance;


import android.weather.app.weatherinfo.WeatherApplication;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.model.DayWeatherInfo;

import java.util.List;

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

    public List<City> getFavoriteCities() {
        return weatherDatabase.getCityDao().getAllCities();
    }
}
