package android.weather.app.weatherinfo.activity;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.utils.Constants;
import android.weather.app.weatherinfo.viewmodel.ViewModel;
import android.weather.app.weatherinfo.viewmodel.WeatherInfoActivityViewModel;
import android.weather.app.weatherinfo.viewmodel.factory.WeatherInfoViewModelFactory;

public class WeatherInfoActivity extends MVVMActivity {
    private WeatherInfoActivityViewModel mWeatherInfoActivityViewModel;

    @NonNull
    @Override
    protected ViewModel getViewModel() {
        City city = getIntent().getParcelableExtra(Constants.EXTRA_CITY);
        mWeatherInfoActivityViewModel = ViewModelProviders.of(this, new WeatherInfoViewModelFactory(city)).get(WeatherInfoActivityViewModel.class);
        return mWeatherInfoActivityViewModel;
    }

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.acitivty_weather_info;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.weather_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
