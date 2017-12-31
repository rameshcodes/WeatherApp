package android.weather.app.weatherinfo.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.text.Editable;
import android.util.Log;
import android.weather.app.weatherinfo.DataSource;
import android.weather.app.weatherinfo.handler.SearchFragmentHandler;
import android.weather.app.weatherinfo.handler.SearchItemHandler;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.networking.response.ZipCodeLatLongResponse;
import android.weather.app.weatherinfo.persistance.DatabaseManager;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SearchViewModel extends android.arch.lifecycle.ViewModel implements AppViewModel, SearchItemHandler {
    private static final String TAG = "SearchViewModel";
    public final ObservableBoolean enableSearchButton = new ObservableBoolean(false);
    public final ObservableBoolean noResults = new ObservableBoolean(false);
    public final ObservableBoolean showLoading = new ObservableBoolean(false);

    private String searchString;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<SearchItemViewModel>> searchItemViewModelList;
    private SearchFragmentHandler mSearchFragmentHandler;

    public SearchViewModel() {

    }

    public void afterTextChanged(Editable s) {
        searchString = s.toString();
        enableSearchButton.set(s.length() > 0);
    }


    public void search() {
        if (searchString.matches("\\d+")) {
            getLatLongForZipCode(Integer.parseInt(searchString));
        } else {
            loadCitiesData(searchString);
        }
    }

    public MutableLiveData<List<SearchItemViewModel>> getSearchItemViewModelList() {
        if (searchItemViewModelList == null) {
            searchItemViewModelList = new MutableLiveData<>();
        }
        return searchItemViewModelList;
    }

    public void setSearchFragmentHandler(SearchFragmentHandler mSearchFragmentHandler) {
        this.mSearchFragmentHandler = mSearchFragmentHandler;
    }

    @Override
    public void onItemClicked(City city) {
        if (mSearchFragmentHandler != null) {
            mSearchFragmentHandler.showForecast(city);
        }
    }

    @Override
    public void onFavorite(SearchItemViewModel searchItemViewModel) {
        if (searchItemViewModel.isFavorite.get()) {
            DatabaseManager.getInstance().deleteCity(searchItemViewModel.city);
            searchItemViewModel.setIsFavorite(false);
        } else {
            DatabaseManager.getInstance().insertCity(searchItemViewModel.city);
            searchItemViewModel.setIsFavorite(true);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    private void getLatLongForZipCode(final int zipCode) {
        showLoading.set(true);
        Disposable d = DataSource.getSearchObservableForZipCode(zipCode).subscribe(new Consumer<ZipCodeLatLongResponse>() {
            @Override
            public void accept(ZipCodeLatLongResponse zipCodeLatLongResponse) throws Exception {
                if (zipCodeLatLongResponse != null && zipCodeLatLongResponse.getLatLongList() != null) {
                    noResults.set(false);
                    String[] latLongValues = zipCodeLatLongResponse.getLatLongList().split(",");
                    City city = new City(zipCode + "", latLongValues[0], latLongValues[1]);
                    onItemClicked(city);
                } else {
                    noResults.set(true);
                }
                showLoading.set(false);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                showLoading.set(false);
                Log.i(TAG, "error: " + throwable.getMessage());
            }
        });
        compositeDisposable.add(d);
    }

    private void loadCitiesData(final String searchString) {
        showLoading.set(true);
        Disposable d = DataSource.getSearchObservableForText(searchString, this).subscribe(new Consumer<List<SearchItemViewModel>>() {
            @Override
            public void accept(List<SearchItemViewModel> cities) throws Exception {
                Log.i(TAG, "accept: Search city list" + cities);
                showLoading.set(false);
                noResults.set(cities.isEmpty());
                if (!cities.isEmpty())
                    searchItemViewModelList.postValue(cities);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                showLoading.set(false);
                Log.i(TAG, "error: " + throwable.getMessage());
            }
        });
        compositeDisposable.add(d);
    }

}
