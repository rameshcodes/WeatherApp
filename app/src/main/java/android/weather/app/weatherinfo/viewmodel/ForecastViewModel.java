package android.weather.app.weatherinfo.viewmodel;


import android.weather.app.weatherinfo.model.DayWeatherInfo;

public class ForecastViewModel implements AppViewModel {

    public final DayWeatherInfo dayWeatherInfo;

    public ForecastViewModel(DayWeatherInfo dayWeatherInfo) {
        this.dayWeatherInfo = dayWeatherInfo;
    }
}
