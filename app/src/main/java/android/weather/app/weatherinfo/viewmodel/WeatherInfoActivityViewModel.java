package android.weather.app.weatherinfo.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.util.Log;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.model.DayWeatherInfo;
import android.weather.app.weatherinfo.networking.RetrofitClient;
import android.weather.app.weatherinfo.networking.request.WeatherInfoRequest;
import android.weather.app.weatherinfo.networking.response.WeatherInfoResponse;
import android.weather.app.weatherinfo.utils.Constants;
import android.weather.app.weatherinfo.utils.Util;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class WeatherInfoActivityViewModel extends android.arch.lifecycle.ViewModel implements ViewModel {
    private static final String TAG = "WeatherInfoActivityView";
    private MutableLiveData<Map<String, DayWeatherInfo>> daysForecastMap;
    public final ObservableBoolean showLoading = new ObservableBoolean(false);

    public WeatherInfoActivityViewModel(City city) {
        getWeatherInfoForLocation(city.getLatitude(), city.getLongitude());
    }

    private void getWeatherInfoForLocation(String lat, String lon) {
        showLoading.set(true);
        final WeatherInfoRequest weatherInfoRequest = RetrofitClient.getClient().create(WeatherInfoRequest.class);
        Observable<WeatherInfoResponse> weatherInfoResponseObservable = weatherInfoRequest.getWeatherInfo(lat, lon, Constants.PRODUCT_VALUE,
                Constants.BEGIN_TIME, Constants.END_TIME, Constants.MAXT_VALUE, Constants.MINT_VALUE, Constants.TEMP_VALUE, Constants.ICONS_VALUE);
        weatherInfoResponseObservable.flatMap(new Function<WeatherInfoResponse, ObservableSource<Map<String, DayWeatherInfo>>>() {
            @Override
            public ObservableSource<Map<String, DayWeatherInfo>> apply(WeatherInfoResponse weatherInfoResponse) throws Exception {
                Log.i(TAG, "accept: " + weatherInfoResponse);
                return Observable.just(Util.convertToWeatherForecastData(weatherInfoResponse.getData()));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Map<String, DayWeatherInfo>>() {
            @Override
            public void accept(Map<String, DayWeatherInfo> dayWeatherInfoMap) throws Exception {
                Log.i(TAG, "accept: " + dayWeatherInfoMap);
                if (!dayWeatherInfoMap.isEmpty()) {
                    daysForecastMap.postValue(dayWeatherInfoMap);
                }
                showLoading.set(false);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "Error: " + throwable.getMessage());
                showLoading.set(false);
            }
        });
    }

    public MutableLiveData<Map<String, DayWeatherInfo>> getDaysForecastMap() {
        if (daysForecastMap == null) {
            daysForecastMap = new MutableLiveData<>();
        }
        return daysForecastMap;
    }
}
