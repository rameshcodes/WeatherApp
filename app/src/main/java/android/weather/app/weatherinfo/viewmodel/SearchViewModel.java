package android.weather.app.weatherinfo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.util.Log;
import android.weather.app.weatherinfo.handler.SearchFragmentHandler;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.networking.RetrofitClient;
import android.weather.app.weatherinfo.networking.request.CitiesRequest;
import android.weather.app.weatherinfo.networking.request.ZipCodeLatLongRequest;
import android.weather.app.weatherinfo.networking.response.CitiesResponse;
import android.weather.app.weatherinfo.networking.response.ZipCodeLatLongResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends android.arch.lifecycle.AndroidViewModel implements ViewModel {
    private static final String TAG = "SearchViewModel";
    public ObservableBoolean enableSearchButton = new ObservableBoolean(false);
    public ObservableBoolean noResults = new ObservableBoolean(false);
    public ObservableBoolean showLoading = new ObservableBoolean(false);

    private String searchString;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<SearchItemViewModel>> searchItemViewModelList;
    private SearchFragmentHandler mSearchFragmentHandler;

    public SearchViewModel(@NonNull Application application) {
        super(application);
    }

    public void afterTextChanged(Editable s) {
        searchString = s.toString();
        enableSearchButton.set(s.length() > 2);
    }

    public void search() {
        if (searchString.matches("\\d+")) {
            getLatLongForZipCode(Integer.parseInt(searchString));
        } else {
            loadCitiesData(searchString);
        }
    }

    private void getLatLongForZipCode(int zipCode) {
        ZipCodeLatLongRequest zipcodeLatLongRequest = RetrofitClient.getClient().create(ZipCodeLatLongRequest.class);
        Observable<ZipCodeLatLongResponse> zipCodeLatLongResponseObservable = zipcodeLatLongRequest.getLatLongForZipcode(zipCode);
        Disposable d = zipCodeLatLongResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ZipCodeLatLongResponse>() {
                    @Override
                    public void accept(ZipCodeLatLongResponse zipCodeLatLongResponse) throws Exception {
                        Log.i(TAG, "accept: " + zipCodeLatLongResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "error: " + throwable.getMessage());
                    }
                });
        compositeDisposable.add(d);
    }

    private void loadCitiesData(final String searchString) {
        showLoading.set(true);
        CitiesRequest citiesRequest = RetrofitClient.getClient().create(CitiesRequest.class);
        Observable<CitiesResponse> citiesResponseObservable = citiesRequest.getCities(12);
        Disposable d = citiesResponseObservable.flatMap(new Function<CitiesResponse, ObservableSource<List<SearchItemViewModel>>>() {
            @Override
            public ObservableSource<List<SearchItemViewModel>> apply(CitiesResponse citiesResponse) throws Exception {
                List<SearchItemViewModel> searchItemViewModelList = new ArrayList<>();
                String[] cities = citiesResponse.getCityNameList().split("\\|");
                String[] latLongList = citiesResponse.getLatLongList().split(" ");
                for (int i = 0; i < cities.length && i < latLongList.length; i++) {
                    if (cities[i].toLowerCase().contains(searchString.toLowerCase())) {
                        String[] latLong = latLongList[i].split(",");
                        City city = new City(cities[i], latLong[0], latLong[1]);
                        SearchItemViewModel searchItemViewModel = new SearchItemViewModel(city, mSearchFragmentHandler);
                        searchItemViewModelList.add(searchItemViewModel);
                    }
                }
                return Observable.just(searchItemViewModelList);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<SearchItemViewModel>>() {
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

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
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
}
