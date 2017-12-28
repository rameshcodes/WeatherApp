package android.weather.app.weatherinfo.fragment;


import android.support.annotation.NonNull;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.viewmodel.ViewModel;
import android.weather.app.weatherinfo.viewmodel.WeatherInfoViewModel;

public class WeatherInfoFragment extends MVVMFragment {
    @NonNull
    @Override
    protected ViewModel getViewModel() {
        return new WeatherInfoViewModel();
    }

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.fragemnt_day_weather_info;
    }
}
