package android.weather.app.weatherinfo.viewmodel;


import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.util.Log;
import android.weather.app.weatherinfo.DataSource;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.model.DayWeatherInfo;
import android.weather.app.weatherinfo.persistance.DatabaseManager;
import android.weather.app.weatherinfo.utils.ActivityUtils;
import android.weather.app.weatherinfo.utils.RxUtil;

import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class WeatherInfoActivityViewModel extends android.arch.lifecycle.ViewModel implements AppViewModel {
    private static final String TAG = "WeatherInfoActivityView";
    public final ObservableBoolean showLoading = new ObservableBoolean(false);
    private MutableLiveData<Map<String, DayWeatherInfo>> daysForecastMap;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public WeatherInfoActivityViewModel(City city, boolean isFavoriteLocation) {
        showLoading.set(true);
        if (isFavoriteLocation) {
            loadForecastDataFromDb(city);
        } else {
            getWeatherInfoForLocationFromServer(city);
        }
    }

    public MutableLiveData<Map<String, DayWeatherInfo>> getDaysForecastMap() {
        if (daysForecastMap == null) {
            daysForecastMap = new MutableLiveData<>();
        }
        return daysForecastMap;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }

    private void loadForecastDataFromDb(final City city) {
        Disposable d = DatabaseManager.getInstance().getWeatherForecastDataForCity(city).compose(RxUtil.schedulersTransformer).
                subscribe(new Consumer<Map<String, DayWeatherInfo>>() {
                    @Override
                    public void accept(Map<String, DayWeatherInfo> dayWeatherInfoMap) throws Exception {
                        Log.i(TAG, "accept: ");
                        showLoading.set(false);
                        daysForecastMap.postValue(dayWeatherInfoMap);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "accept: ");
                        getWeatherInfoForLocationFromServer(city);
                    }
                });
        compositeDisposable.add(d);
    }

    private void getWeatherInfoForLocationFromServer(final City city) {
        Disposable d = DataSource.getWeatherForecastDataForSingleLocation(city).subscribe(new Consumer<Map<String, DayWeatherInfo>>() {
            @Override
            public void accept(Map<String, DayWeatherInfo> dayWeatherInfoMap) throws Exception {
                Log.i(TAG, "accept: " + dayWeatherInfoMap);
                if (!dayWeatherInfoMap.isEmpty()) {
                    daysForecastMap.postValue(dayWeatherInfoMap);
                } else {
                    ActivityUtils.handleError(new Throwable("No data found"));
                }
                showLoading.set(false);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "Error: " + throwable.getMessage());
                ActivityUtils.handleError(throwable);
                showLoading.set(false);
            }
        });
        compositeDisposable.add(d);
    }
}
