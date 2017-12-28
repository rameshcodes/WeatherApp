package android.weather.app.weatherinfo.viewmodel;

import android.util.Log;
import android.weather.app.weatherinfo.handler.DashboardHandler;
import android.weather.app.weatherinfo.model.Point;
import android.weather.app.weatherinfo.networking.RetrofitClient;
import android.weather.app.weatherinfo.networking.request.WeatherInfoRequest;
import android.weather.app.weatherinfo.networking.response.WeatherInfoResponse;
import android.weather.app.weatherinfo.utils.Constants;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DashboardViewModel implements ViewModel {
    private static final String TAG = "DashboardViewModel";
    private DashboardHandler mDashboardHandler;

    public DashboardViewModel(DashboardHandler dashboardHandler) {
        mDashboardHandler = dashboardHandler;
        Point location = new Point();
        location.setLatitude("38.99");
        location.setLongitude("-77.01");
        getWeatherInfoForLocation(location);
    }

    public void favoriteClick() {
        mDashboardHandler.showFavouritesFragment();
    }

    public void searchClick() {
        mDashboardHandler.loadSearchFragment();
    }

    private void getWeatherInfoForLocation(Point point) {
        final WeatherInfoRequest weatherInfoRequest = RetrofitClient.getClient().create(WeatherInfoRequest.class);
        Observable<WeatherInfoResponse> weatherInfoResponseObservable = weatherInfoRequest.getWeatherInfo(point.getLatitude(), point.getLongitude(), Constants.PRODUCT_VALUE,
                Constants.BEGIN_TIME, Constants.END_TIME, Constants.MAXT_VALUE, Constants.MINT_VALUE,Constants.TEMP_VALUE, Constants.ICONS_VALUE);
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
