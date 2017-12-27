package android.weather.app.weatherinfo.viewmodel;

import android.util.Log;
import android.weather.app.weatherinfo.model.Point;
import android.weather.app.weatherinfo.networking.RetrofitClient;
import android.weather.app.weatherinfo.networking.request.CitiesRequest;
import android.weather.app.weatherinfo.networking.request.WeatherInfoRequest;
import android.weather.app.weatherinfo.networking.request.ZipCodeLatLongRequest;
import android.weather.app.weatherinfo.networking.response.CitiesResponse;
import android.weather.app.weatherinfo.networking.response.WeatherInfoResponse;
import android.weather.app.weatherinfo.networking.response.ZipCodeLatLongResponse;
import android.weather.app.weatherinfo.utils.Constants;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DashboardViewModel implements ViewModel {
    private static final String TAG = "DashboardViewModel";

    public DashboardViewModel() {
//        loadCitiesData();
//        getLatLongForZipCode();
        Point location = new Point();
        location.setLatitude("38.99");
        location.setLongitude("-77.01");
        getWeatherInfoForLocation(location);
    }

    private void loadCitiesData() {
        CitiesRequest citiesRequest = RetrofitClient.getClient().create(CitiesRequest.class);
        Observable<CitiesResponse> citiesResponseObservable = citiesRequest.getCities(12);
        Disposable d = citiesResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<CitiesResponse>() {
                    @Override
                    public void accept(CitiesResponse citiesResponse) throws Exception {
                        Log.i(TAG, "accept: " + citiesResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "error: " + throwable.getMessage());
                    }
                });
    }

    private void getLatLongForZipCode() {
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

    private void getWeatherInfoForLocation(Point point) {
        final WeatherInfoRequest weatherInfoRequest = RetrofitClient.getClient().create(WeatherInfoRequest.class);
        Observable<WeatherInfoResponse> weatherInfoResponseObservable = weatherInfoRequest.getWeatherInfo(point.getLatitude(), point.getLongitude(), Constants.PRODUCT_VALUE,
                Constants.BEGIN_TIME, Constants.END_TIME, Constants.MAXT_VALUE, Constants.MINT_VALUE, Constants.ICONS_VALUE);
        weatherInfoResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<WeatherInfoResponse>() {
            @Override
            public void accept(WeatherInfoResponse weatherInfoResponse) throws Exception {
                Log.i(TAG, "accept: " + weatherInfoResponse);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "Error: " + throwable.getMessage());
            }
        });
    }
}
