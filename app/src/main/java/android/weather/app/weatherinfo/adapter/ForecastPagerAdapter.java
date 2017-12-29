package android.weather.app.weatherinfo.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.weather.app.weatherinfo.fragment.ForecastFragment;
import android.weather.app.weatherinfo.model.DayWeatherInfo;

import java.util.List;

public class ForecastPagerAdapter extends FragmentStatePagerAdapter {

    private final List<DayWeatherInfo> forecastViewModelList;

    public ForecastPagerAdapter(FragmentManager fm, List<DayWeatherInfo> forecastViewModelList) {
        super(fm);
        this.forecastViewModelList = forecastViewModelList;
    }

    @Override
    public Fragment getItem(int position) {
        return ForecastFragment.getInstance(forecastViewModelList.get(position));
    }

    @Override
    public int getCount() {
        if (forecastViewModelList != null)
            return forecastViewModelList.size();
        return 0;
    }
}
