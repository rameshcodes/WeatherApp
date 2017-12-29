package android.weather.app.weatherinfo.viewmodel;


import android.util.Log;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.networking.RetrofitClient;
import android.weather.app.weatherinfo.networking.request.WeatherInfoRequest;
import android.weather.app.weatherinfo.networking.response.WeatherInfoResponse;
import android.weather.app.weatherinfo.utils.Constants;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class WeatherInfoActivityViewModel extends android.arch.lifecycle.ViewModel implements ViewModel {
    private static final String TAG = "WeatherInfoActivityView";

    public WeatherInfoActivityViewModel(City city) {
        getWeatherInfoForLocation(city.getLatitude(), city.getLongitude());
    }

    private void getWeatherInfoForLocation(String lat, String lon) {
        final WeatherInfoRequest weatherInfoRequest = RetrofitClient.getClient().create(WeatherInfoRequest.class);
        Observable<WeatherInfoResponse> weatherInfoResponseObservable = weatherInfoRequest.getWeatherInfo(lat, lon, Constants.PRODUCT_VALUE,
                Constants.BEGIN_TIME, Constants.END_TIME, Constants.MAXT_VALUE, Constants.MINT_VALUE, Constants.TEMP_VALUE, Constants.ICONS_VALUE);
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
