package android.weather.app.weatherinfo.adapter;


import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.viewmodel.HourlyTempViewModel;

import java.util.List;

public class HourlyTempAdapter extends BaseAdapter {

    private List<HourlyTempViewModel> hourlyTempViewModelList;

    public HourlyTempAdapter(List<HourlyTempViewModel> hourlyTempViewModelList) {
        this.hourlyTempViewModelList = hourlyTempViewModelList;
    }

    public void setHourlyTempViewModelList(List<HourlyTempViewModel> hourlyTempViewModelList) {
        this.hourlyTempViewModelList = hourlyTempViewModelList;
        notifyDataSetChanged();
    }

    @Override
    protected Object getObjForPosition(int position) {
        if (hourlyTempViewModelList != null)
            hourlyTempViewModelList.get(position);
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
