package android.weather.app.weatherinfo.adapter;


import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.model.HourWeatherInfo;
import android.weather.app.weatherinfo.viewmodel.HourlyTempViewModel;

import java.util.ArrayList;
import java.util.List;

public class HourlyTempAdapter extends BaseAdapter {

    private List<HourlyTempViewModel> hourlyTempViewModelList;

    public HourlyTempAdapter(List<HourWeatherInfo> hourWeatherInfoList) {
        this.hourlyTempViewModelList = new ArrayList<>();
        convertIntoViewModels(hourWeatherInfoList);
    }

    private void convertIntoViewModels(List<HourWeatherInfo> hourWeatherInfoList) {
        for (HourWeatherInfo info : hourWeatherInfoList) {
            this.hourlyTempViewModelList.add(new HourlyTempViewModel(info));
        }

    }

    @Override
    protected Object getObjForPosition(int position) {
        if (hourlyTempViewModelList != null)
            return hourlyTempViewModelList.get(position);
        return null;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.ri_hour_temp;
    }

    @Override
    public int getItemCount() {
        if (hourlyTempViewModelList != null)
            return hourlyTempViewModelList.size();
        return 0;
    }
}
