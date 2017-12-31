package android.weather.app.weatherinfo.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.util.Log;
import android.weather.app.weatherinfo.DataSource;
import android.weather.app.weatherinfo.handler.FavoriteHandler;
import android.weather.app.weatherinfo.handler.FavoriteItemHandler;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.model.Data;
import android.weather.app.weatherinfo.persistance.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FavoriteViewModel extends android.arch.lifecycle.ViewModel implements ViewModel, FavoriteItemHandler {
    private static final String TAG = "FavoriteViewModel";

    public final ObservableBoolean noResults = new ObservableBoolean(false);
    private MutableLiveData<List<FavoriteItemViewModel>> listLiveData;
    private FavoriteHandler favoriteHandler;

    public FavoriteViewModel() {
        loadFavoriteCities();
    }

    public LiveData<List<FavoriteItemViewModel>> getListLiveData() {
        if (listLiveData == null) {
            listLiveData = new MutableLiveData<>();
        }
        return listLiveData;
    }

    public void setFavoriteHandler(FavoriteHandler favoriteHandler) {
        this.favoriteHandler = favoriteHandler;
    }

    private void loadFavoriteCities() {
        DataSource.loadFavoritesDataObservable(this).subscribe(new Consumer<List<FavoriteItemViewModel>>() {
            @Override
            public void accept(List<FavoriteItemViewModel> favoriteItemViewModels) throws Exception {
                Log.i(TAG, "accept: " + favoriteItemViewModels);
                noResults.set(favoriteItemViewModels.isEmpty());
                if (!favoriteItemViewModels.isEmpty())
                    listLiveData.postValue(favoriteItemViewModels);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable.getMessage());
            }
        });
    }

    @Override
    public void onFavoriteSelected(City city) {
        if (favoriteHandler != null) {
            favoriteHandler.favoriteClick(city);
        }
    }
}
