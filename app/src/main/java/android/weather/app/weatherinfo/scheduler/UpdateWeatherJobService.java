package android.weather.app.weatherinfo.scheduler;

import android.util.Log;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.model.DayWeatherInfo;
import android.weather.app.weatherinfo.model.Location;
import android.weather.app.weatherinfo.networking.RetrofitClient;
import android.weather.app.weatherinfo.networking.request.MultiLocationWeatherInfoRequest;
import android.weather.app.weatherinfo.networking.response.WeatherInfoResponse;
import android.weather.app.weatherinfo.persistance.DatabaseManager;
import android.weather.app.weatherinfo.utils.Constants;
import android.weather.app.weatherinfo.utils.Util;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class UpdateWeatherJobService extends JobService {
    private static final String TAG = "UpdateWeatherService";
    private JobParameters mJobParameters;

    @Override
    public boolean onStartJob(JobParameters job) {
        Log.i(TAG, "onStartJob called ");
        mJobParameters = job;
        getFavoriteCities();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        Log.i(TAG, "onStopJob called");
        return false;
    }

    private void getFavoriteCities() {
        Observable citiesObservable = DatabaseManager.getInstance().getFavoriteCities();
        citiesObservable.flatMap(new Function() {
            @Override
            public Object apply(Object o) throws Exception {
                List<City> cityList = (List<City>) o;
                StringBuilder latLongBuilder = new StringBuilder();
                for (int i = 0; i < cityList.size(); i++) {
                    City city = cityList.get(i);
                    latLongBuilder.append(city.getLatitude()).append(",").append(city.getLatitude());
                    if (i != cityList.size() - 1) {
                        latLongBuilder.append(" ");
                    }
                }
                return Observable.just(latLongBuilder.toString());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() {
            @Override
            public void accept(Object latLongValues) throws Exception {
                Log.i(TAG, "accept: latlong values " + latLongValues);
                updateFavoritesForecast((String) latLongValues);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "on Error " + throwable.getMessage());
            }
        });
    }

    private void updateFavoritesForecast(String latLongValues) {
        final MultiLocationWeatherInfoRequest multiLocationWeatherInfoRequest = RetrofitClient.getClient().create(MultiLocationWeatherInfoRequest.class);
        Observable<WeatherInfoResponse> weatherInfoResponseObservable = multiLocationWeatherInfoRequest.getWeatherInfo(latLongValues, Constants.PRODUCT_VALUE,
                Constants.BEGIN_TIME, Constants.END_TIME, Constants.MAXT_VALUE, Constants.MINT_VALUE, Constants.TEMP_VALUE, Constants.ICONS_VALUE);
        weatherInfoResponseObservable.any(new Predicate<WeatherInfoResponse>() {
            @Override
            public boolean test(WeatherInfoResponse weatherInfoResponse) throws Exception {
                Log.i(TAG, "accept: " + weatherInfoResponse);
                if (weatherInfoResponse.getData() == null) {
                    return false;
                }
                Map<Location, Map<String, DayWeatherInfo>> locationWeatherInfoMap = Util.convertToWeatherForecastData(weatherInfoResponse.getData());
                DatabaseManager.getInstance().updateWeatherForecastInfo(locationWeatherInfoMap);
                return true;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean status) throws Exception {
                Log.i(TAG, "accept: update status : " + status);
                jobFinished(mJobParameters, true);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "Error: " + throwable.getMessage());
                jobFinished(mJobParameters, true);
            }
        });
    }
}
