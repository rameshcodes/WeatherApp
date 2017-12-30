package android.weather.app.weatherinfo.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.weather.app.weatherinfo.handler.FavoriteHandler;
import android.weather.app.weatherinfo.handler.FavoriteItemHandler;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.persistance.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FavoriteViewModel extends android.arch.lifecycle.ViewModel implements ViewModel,FavoriteItemHandler {
    private static final String TAG = "FavoriteViewModel";
    private MutableLiveData<List<FavoriteItemViewModel>> listLiveData;
    private FavoriteHandler favoriteHandler;
    public FavoriteViewModel() {
        loadFavoriteCities();
    }

    public LiveData<List<FavoriteItemViewModel>> getListLiveData() {
        if(listLiveData ==null){
            listLiveData = new MutableLiveData<>();
        }
        return listLiveData;
    }

    public void setFavoriteHandler(FavoriteHandler favoriteHandler) {
        this.favoriteHandler = favoriteHandler;
    }

    private void loadFavoriteCities(){
        Observable citiesObservable = DatabaseManager.getInstance().getFavoriteCities();
        citiesObservable.flatMap(new Function() {
            @Override
            public Observable<List<FavoriteItemViewModel>> apply(Object o) throws Exception {
                List<City> cityList = (List<City>) o;
                List<FavoriteItemViewModel> favoriteItemViewModels = new ArrayList<>();
                for (City city: cityList) {
                    favoriteItemViewModels.add(new FavoriteItemViewModel(city,FavoriteViewModel.this));
                }
                return Observable.just(favoriteItemViewModels);
            }
        }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                List<FavoriteItemViewModel> favoriteItemViewModels = (List<FavoriteItemViewModel>) o;
                Log.i(TAG, "accept: "+favoriteItemViewModels);
                listLiveData.postValue(favoriteItemViewModels);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: "+throwable.getMessage());
            }
        });
    }

    @Override
    public void onFavoriteSelected(City city) {
        if (favoriteHandler!=null){
            favoriteHandler.favoriteClick(city);
        }
    }
}
