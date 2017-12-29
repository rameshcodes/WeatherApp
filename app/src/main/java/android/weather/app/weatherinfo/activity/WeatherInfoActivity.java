package android.weather.app.weatherinfo.activity;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.adapter.DayListForecastAdapter;
import android.weather.app.weatherinfo.adapter.ForecastPagerAdapter;
import android.weather.app.weatherinfo.databinding.AcitivtyWeatherInfoBinding;
import android.weather.app.weatherinfo.handler.DayListHandler;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.model.DayWeatherInfo;
import android.weather.app.weatherinfo.utils.Constants;
import android.weather.app.weatherinfo.view.DayRecyclerView;
import android.weather.app.weatherinfo.viewmodel.ViewModel;
import android.weather.app.weatherinfo.viewmodel.WeatherInfoActivityViewModel;
import android.weather.app.weatherinfo.viewmodel.factory.WeatherInfoViewModelFactory;

import java.util.ArrayList;
import java.util.Map;

public class WeatherInfoActivity extends MVVMActivity implements DayListHandler {
    private WeatherInfoActivityViewModel mWeatherInfoActivityViewModel;
    private DayListForecastAdapter dayListForecastAdapter;
    private ForecastPagerAdapter forecastPagerAdapter;

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
        subscribeToForecastData();
    }

    private void subscribeToForecastData() {
        mWeatherInfoActivityViewModel.getDaysForecastMap().observe(this, new Observer<Map<String, DayWeatherInfo>>() {
            @Override
            public void onChanged(@Nullable Map<String, DayWeatherInfo> dayWeatherInfoMap) {
                setUpViews(dayWeatherInfoMap);
            }
        });
    }

    private void setUpViews(Map<String, DayWeatherInfo> dayWeatherInfoMap) {
        DayRecyclerView dayRecyclerView = ((AcitivtyWeatherInfoBinding) mBinding).dayRecyclerView;
        dayListForecastAdapter = new DayListForecastAdapter(dayWeatherInfoMap.keySet(),this);
        forecastPagerAdapter = new ForecastPagerAdapter(getSupportFragmentManager(), new ArrayList<DayWeatherInfo>(dayWeatherInfoMap.values()));
        dayRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dayRecyclerView.setAdapter(dayListForecastAdapter);
        ((AcitivtyWeatherInfoBinding) mBinding).dayForecastViewPager.setAdapter(forecastPagerAdapter);
        dayRecyclerView.setUpWithViewPager(((AcitivtyWeatherInfoBinding) mBinding).dayForecastViewPager, this);
        onDaySelected(0);
    }


    @Override
    public void onDaySelected(int position) {
        ((AcitivtyWeatherInfoBinding) mBinding).dayForecastViewPager.setCurrentItem(position, true);
        dayListForecastAdapter.setSelectedItem(position);
        ((AcitivtyWeatherInfoBinding) mBinding).dayRecyclerView.scrollToPosition(position);
    }

    @Override
    public void onDayPagerSwiped(int position) {
        dayListForecastAdapter.setSelectedItem(position);
        ((AcitivtyWeatherInfoBinding) mBinding).dayRecyclerView.scrollToPosition(position);
    }
}
