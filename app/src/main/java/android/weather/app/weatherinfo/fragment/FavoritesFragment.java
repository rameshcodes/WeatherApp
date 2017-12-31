package android.weather.app.weatherinfo.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.weather.app.weatherinfo.R;
import android.weather.app.weatherinfo.activity.WeatherInfoActivity;
import android.weather.app.weatherinfo.adapter.FavoriteAdapter;
import android.weather.app.weatherinfo.databinding.FragmentFavoritesBinding;
import android.weather.app.weatherinfo.handler.FavoriteHandler;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.utils.Constants;
import android.weather.app.weatherinfo.viewmodel.FavoriteItemViewModel;
import android.weather.app.weatherinfo.viewmodel.FavoriteViewModel;
import android.weather.app.weatherinfo.viewmodel.ViewModel;

import java.util.List;

public class FavoritesFragment extends MVVMFragment {

    private FavoriteAdapter favoriteAdapter;
    private FavoriteViewModel favoriteViewModel;
    private FavoriteHandler favoriteItemHandler = new FavoriteHandler() {
        @Override
        public void favoriteClick(City city) {
            Intent intent = new Intent(getContext(), WeatherInfoActivity.class);
            intent.putExtra(Constants.EXTRA_CITY, city);
            intent.putExtra(Constants.EXTRA_IS_FROM_FAVORITES_SCREEN,true);
            startActivity(intent);
        }
    };

    @NonNull
    @Override
    protected ViewModel getViewModel() {
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        favoriteViewModel.setFavoriteHandler(favoriteItemHandler);
        return favoriteViewModel;
    }

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_favorites;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.fav_title);
        subscribeToData();
    }

    @Override
    public void onDestroyView() {
        favoriteViewModel.setFavoriteHandler(null);
        super.onDestroyView();
    }

    private void subscribeToData(){
        favoriteViewModel.getListLiveData().observe(this, new Observer<List<FavoriteItemViewModel>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteItemViewModel> favoriteItemViewModels) {
             setUpList(favoriteItemViewModels);
            }
        });
    }
    private void setUpList(List<FavoriteItemViewModel> favoriteItemViewModelList) {
        favoriteAdapter = new FavoriteAdapter(favoriteItemViewModelList);
        ((FragmentFavoritesBinding) mBinding).hourlyTempRecyclerView.setAdapter(favoriteAdapter);
    }
}
