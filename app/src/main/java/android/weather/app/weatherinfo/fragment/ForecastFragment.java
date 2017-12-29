package android.weather.app.weatherinfo.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.adapter.HourlyTempAdapter;
import android.weather.app.weatherinfo.databinding.FragemntDayWeatherInfoBinding;
import android.weather.app.weatherinfo.model.DayWeatherInfo;
import android.weather.app.weatherinfo.utils.Constants;
import android.weather.app.weatherinfo.viewmodel.ForecastViewModel;
import android.weather.app.weatherinfo.viewmodel.ViewModel;

public class ForecastFragment extends MVVMFragment {
    private HourlyTempAdapter hourlyTempAdapter;


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
        return new ForecastViewModel((DayWeatherInfo) getArguments().getParcelable(Constants.EXTRA_FORECAST_DATA));
    }

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.fragemnt_day_weather_info;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DayWeatherInfo dayWeatherInfo = getArguments().getParcelable(Constants.EXTRA_FORECAST_DATA);
        hourlyTempAdapter = new HourlyTempAdapter(dayWeatherInfo.getHourWeatherInfoList());
        ((FragemntDayWeatherInfoBinding) mBinding).hourlyTempRecyclerView.setAdapter(hourlyTempAdapter);
    }
}
