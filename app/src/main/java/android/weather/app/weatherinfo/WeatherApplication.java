package android.weather.app.weatherinfo;

import android.app.Application;
import android.content.Context;
import android.weather.app.weatherinfo.binding.BindingUtils;
import android.weather.app.weatherinfo.scheduler.WeatherUpdateJobScheduler;

public class WeatherApplication extends Application {
    private static Context applicationContext;

    public static Context getContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        BindingUtils.setDefaultBinder();
        WeatherUpdateJobScheduler.scheduleJob(getApplicationContext());
    }
}
