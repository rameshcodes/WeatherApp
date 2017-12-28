package android.weather.app.weatherinfo.viewmodel;


import android.weather.app.weatherinfo.model.HourWeatherInfo;

public class HourlyTempViewModel implements ViewModel {
    public final HourWeatherInfo hourWeatherInfo;

    public HourlyTempViewModel(HourWeatherInfo hourWeatherInfo) {
        this.hourWeatherInfo = hourWeatherInfo;
    }
}
