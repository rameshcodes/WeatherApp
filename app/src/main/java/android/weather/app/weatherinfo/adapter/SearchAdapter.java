package android.weather.app.weatherinfo.adapter;

import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.viewmodel.SearchItemViewModel;

import java.util.List;


public class SearchAdapter extends BaseAdapter {

    private List<SearchItemViewModel> searchItemViewModelList;

    public SearchAdapter(List<SearchItemViewModel> searchItemViewModelList) {
        this.searchItemViewModelList = searchItemViewModelList;
    }

    public void setSearchItemViewModelList(List<SearchItemViewModel> searchItemViewModelList) {
        this.searchItemViewModelList = searchItemViewModelList;
        notifyDataSetChanged();
    }

    @Override
    protected Object getObjForPosition(int position) {
        if (searchItemViewModelList != null)
            return searchItemViewModelList.get(position);
        return null;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.ri_search;
    }

    @Override
    public int getItemCount() {
        if (searchItemViewModelList != null)
            return searchItemViewModelList.size();
        return 0;
    }
}
