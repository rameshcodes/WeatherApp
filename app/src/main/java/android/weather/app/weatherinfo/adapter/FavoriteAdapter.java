package android.weather.app.weatherinfo.adapter;


import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.viewmodel.FavoriteItemViewModel;

import java.util.List;

public class FavoriteAdapter extends BaseAdapter {
    private final List<FavoriteItemViewModel> favoriteItemViewModelList;

    public FavoriteAdapter(List<FavoriteItemViewModel> favoriteItemViewModelList) {
        this.favoriteItemViewModelList = favoriteItemViewModelList;
    }

    @Override
    protected Object getObjForPosition(int position) {
        if (this.favoriteItemViewModelList != null) {
            return favoriteItemViewModelList.get(position);
        }
        return null;
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return R.layout.ri_favorite;
    }

    @Override
    public int getItemCount() {
        if (this.favoriteItemViewModelList != null) {
            return favoriteItemViewModelList.size();
        }
        return 0;
    }
}
