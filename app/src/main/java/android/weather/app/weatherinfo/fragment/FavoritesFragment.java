package android.weather.app.weatherinfo.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.adapter.FavoriteAdapter;
import android.weather.app.weatherinfo.databinding.FragmentFavoritesBinding;
import android.weather.app.weatherinfo.viewmodel.FavoriteViewModel;
import android.weather.app.weatherinfo.viewmodel.ViewModel;

public class FavoritesFragment extends MVVMFragment {

    private FavoriteAdapter favoriteAdapter;

    @NonNull
    @Override
    protected ViewModel getViewModel() {
        return new FavoriteViewModel();
    }

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorites;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.fav_title);
    }

    private void setUpList() {
        favoriteAdapter = new FavoriteAdapter(null);
        ((FragmentFavoritesBinding) mBinding).hourlyTempRecyclerView.setAdapter(favoriteAdapter);
    }
}
