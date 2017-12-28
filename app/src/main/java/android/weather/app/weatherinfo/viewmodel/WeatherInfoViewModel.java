package android.weather.app.weatherinfo.viewmodel;


import android.weather.app.weatherinfo.model.DayWeatherInfo;

public class WeatherInfoViewModel implements ViewModel {

    public DayWeatherInfo dayWeatherInfo;

    public WeatherInfoViewModel() {
    }

    public void setDayWeatherInfo(DayWeatherInfo dayWeatherInfo) {
        this.dayWeatherInfo = dayWeatherInfo;
    }
}
