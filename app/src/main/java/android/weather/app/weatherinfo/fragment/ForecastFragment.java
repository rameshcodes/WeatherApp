package android.weather.app.weatherinfo.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.model.DayWeatherInfo;
import android.weather.app.weatherinfo.utils.Constants;
import android.weather.app.weatherinfo.viewmodel.ForecastViewModel;
import android.weather.app.weatherinfo.viewmodel.ViewModel;

public class ForecastFragment extends MVVMFragment {

    public static ForecastFragment getInstance(DayWeatherInfo dayWeatherInfo) {
        ForecastFragment forecastFragment = new ForecastFragment();
        Bundle data = new Bundle();
        data.putParcelable(Constants.EXTRA_FORECAST_DATA, dayWeatherInfo);
        forecastFragment.setArguments(data);
        return forecastFragment;
    }

    @NonNull
    @Override
    protected ViewModel getViewModel() {
        DayWeatherInfo dayWeatherInfo = getArguments().getParcelable(Constants.EXTRA_FORECAST_DATA);
        return new ForecastViewModel(dayWeatherInfo);
    }

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.fragemnt_day_weather_info;
    }
}
