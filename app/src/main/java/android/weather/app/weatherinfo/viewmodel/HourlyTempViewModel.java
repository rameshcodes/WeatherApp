package android.weather.app.weatherinfo.viewmodel;


import android.weather.app.weatherinfo.model.HourWeatherInfo;
import android.weather.app.weatherinfo.utils.Util;

public class HourlyTempViewModel implements AppViewModel {
    public final HourWeatherInfo hourWeatherInfo;

    public HourlyTempViewModel(HourWeatherInfo hourWeatherInfo) {
        this.hourWeatherInfo = hourWeatherInfo;
    }

    public String getTime() {
        return Util.getTime(hourWeatherInfo.getTime());
    }
}
