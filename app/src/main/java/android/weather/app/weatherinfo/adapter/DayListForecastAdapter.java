package android.weather.app.weatherinfo.adapter;


import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.handler.DayListHandler;
import android.weather.app.weatherinfo.viewmodel.DayTabViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DayListForecastAdapter extends BaseAdapter {
    private List<DayTabViewModel> dayTabViewModelList;

    public DayListForecastAdapter(Set<String> daysSet, DayListHandler dayListHandler) {
        this.dayTabViewModelList = new ArrayList<>();
        convertIntoViewModel(daysSet, dayListHandler);
    }

    private void convertIntoViewModel(Set<String> daysSet, DayListHandler dayListHandler) {
        int index = 0;
        for (String day : daysSet) {
            dayTabViewModelList.add(new DayTabViewModel(day, index++, dayListHandler));
        }
    }

    @Override
    protected Object getObjForPosition(int position) {
        if (dayTabViewModelList != null) {
            return dayTabViewModelList.get(position);
        }
        return null;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.ri_day_tab;
    }

    @Override
    public int getItemCount() {
        if (dayTabViewModelList != null) {
            return dayTabViewModelList.size();
        }
        return 0;
    }

    public void setSelectedItem(int position) {
        for (int i = 0; i < dayTabViewModelList.size(); i++) {
            dayTabViewModelList.get(i).setSelected(i == position);
        }
    }
}
