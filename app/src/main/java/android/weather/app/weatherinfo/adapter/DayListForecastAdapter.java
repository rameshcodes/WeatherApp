package android.weather.app.weatherinfo.adapter;


import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.viewmodel.DayTabViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DayListForecastAdapter extends BaseAdapter {
    private List<DayTabViewModel> dayTabViewModelList;

    public DayListForecastAdapter(Set<String> daysSet) {
        this.dayTabViewModelList = new ArrayList<>();
        convertIntoViewModel(daysSet);
    }

    private void convertIntoViewModel(Set<String> daysSet) {
        for (String day : daysSet) {
            dayTabViewModelList.add(new DayTabViewModel(day));
        }
    }
    
    @Override
    protected Object getObjForPosition(int position) {
        if (dayTabViewModelList != null) {
            dayTabViewModelList.get(position);
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
}
