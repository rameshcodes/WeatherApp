package android.weather.app.weatherinfo.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
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
import android.weather.app.weatherinfo.viewmodel.AppViewModel;
import android.weather.app.weatherinfo.viewmodel.WeatherInfoActivityViewModel;
import android.weather.app.weatherinfo.viewmodel.factory.WeatherInfoViewModelFactory;

import java.util.ArrayList;
import java.util.Map;

public class WeatherInfoActivity extends MVVMActivity implements DayListHandler {
    private static final String SELECTED_DAY = "SelectedMonth";

    private WeatherInfoActivityViewModel mWeatherInfoActivityViewModel;
    private DayListForecastAdapter dayListForecastAdapter;
    private ForecastPagerAdapter forecastPagerAdapter;
    private ViewPager dayForecastViewPager;
    private int selectedPosition = 0;

    @NonNull
    @Override
    protected AppViewModel getViewModel() {
        City city = getIntent().getParcelableExtra(Constants.EXTRA_CITY);
        boolean isFromFavoriteScreen = getIntent().getBooleanExtra(Constants.EXTRA_IS_FROM_FAVORITES_SCREEN, false);
        mWeatherInfoActivityViewModel = ViewModelProviders.of(this, new WeatherInfoViewModelFactory(city, isFromFavoriteScreen)).get(WeatherInfoActivityViewModel.class);
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
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_DAY)) {
            selectedPosition = savedInstanceState.getInt(SELECTED_DAY);
        }
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
        dayForecastViewPager = ((AcitivtyWeatherInfoBinding) mBinding).dayForecastViewPager;
        dayListForecastAdapter = new DayListForecastAdapter(dayWeatherInfoMap.keySet(), this);
        forecastPagerAdapter = new ForecastPagerAdapter(getSupportFragmentManager(), new ArrayList<DayWeatherInfo>(dayWeatherInfoMap.values()));
        dayRecyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dayRecyclerView.setAdapter(dayListForecastAdapter);
        dayForecastViewPager.setAdapter(forecastPagerAdapter);
        dayRecyclerView.setUpWithViewPager(((AcitivtyWeatherInfoBinding) mBinding).dayForecastViewPager, this);
        onDaySelected(selectedPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (dayForecastViewPager != null)
            outState.putInt(SELECTED_DAY, dayForecastViewPager.getCurrentItem());
    }


    @Override
    public void onDaySelected(int position) {
        ((AcitivtyWeatherInfoBinding) mBinding).dayForecastViewPager.setCurrentItem(position, true);
        dayListForecastAdapter.setSelectedItem(position);
        ((AcitivtyWeatherInfoBinding) mBinding).dayRecyclerView.smoothScrollToPosition(position);
    }

    @Override
    public void onDayPagerSwiped(int position) {
        dayListForecastAdapter.setSelectedItem(position);
        ((AcitivtyWeatherInfoBinding) mBinding).dayRecyclerView.smoothScrollToPosition(position);
    }
}
