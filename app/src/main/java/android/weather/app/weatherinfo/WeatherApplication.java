package android.weather.app.weatherinfo;

import android.app.Application;
import android.weather.app.weatherinfo.binding.BindingUtils;

public class WeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BindingUtils.setDefaultBinder();
    }
}
