package android.weather.app.weatherinfo.viewmodel;

import android.databinding.ObservableBoolean;
import android.text.Editable;
import android.util.Log;
import android.weather.app.weatherinfo.networking.RetrofitClient;
import android.weather.app.weatherinfo.networking.request.ZipCodeLatLongRequest;
import android.weather.app.weatherinfo.networking.response.ZipCodeLatLongResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by R Ramesh on 26/12/17.
 */

public class SearchViewModel implements ViewModel {
    private static final String TAG = "SearchViewModel";
    public ObservableBoolean enableSearchButton = new ObservableBoolean(false);
    private String searchString;

    public SearchViewModel() {
    }

    public void afterTextChanged(Editable s) {
        searchString = s.toString();
        enableSearchButton.set(s.length() > 2);
    }

    public void search() {
        if (searchString.matches("^-?\\d+$")) {

        } else {
            getLatLongForZipCode(Integer.parseInt(searchString));
        }
    }

    private void getLatLongForZipCode(int zipCode) {
        ZipCodeLatLongRequest zipcodeLatLongRequest = RetrofitClient.getClient().create(ZipCodeLatLongRequest.class);
        Observable<ZipCodeLatLongResponse> zipCodeLatLongResponseObservable = zipcodeLatLongRequest.getLatLongForZipcode(20912);
        zipCodeLatLongResponseObservable.subscribeOn(Schedulers.io())
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
    }
}
