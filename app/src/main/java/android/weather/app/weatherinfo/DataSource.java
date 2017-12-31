package android.weather.app.weatherinfo;

import android.util.Log;
import android.weather.app.weatherinfo.handler.FavoriteItemHandler;
import android.weather.app.weatherinfo.handler.SearchItemHandler;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.model.DayWeatherInfo;
import android.weather.app.weatherinfo.model.Location;
import android.weather.app.weatherinfo.networking.RetrofitClient;
import android.weather.app.weatherinfo.networking.request.CitiesRequest;
import android.weather.app.weatherinfo.networking.request.MultiLocationWeatherInfoRequest;
import android.weather.app.weatherinfo.networking.request.WeatherInfoRequest;
import android.weather.app.weatherinfo.networking.request.ZipCodeLatLongRequest;
import android.weather.app.weatherinfo.networking.response.CitiesResponse;
import android.weather.app.weatherinfo.networking.response.WeatherInfoResponse;
import android.weather.app.weatherinfo.networking.response.ZipCodeLatLongResponse;
import android.weather.app.weatherinfo.persistance.DatabaseManager;
import android.weather.app.weatherinfo.utils.Constants;
import android.weather.app.weatherinfo.utils.RxUtil;
import android.weather.app.weatherinfo.utils.Util;
import android.weather.app.weatherinfo.viewmodel.FavoriteItemViewModel;
import android.weather.app.weatherinfo.viewmodel.SearchItemViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class DataSource {
    private static final String TAG = "DataSource";

    public static Observable getSearchObservableForText(final String searchString, final SearchItemHandler searchItemHandler) {
        final CitiesRequest citiesRequest = RetrofitClient.getClient().create(CitiesRequest.class);
        Observable<CitiesResponse> citiesResponseObservable = citiesRequest.getCities(12);
        return Observable.zip(citiesResponseObservable, DatabaseManager.getInstance().getFavoriteCities(), new BiFunction<CitiesResponse, List<City>, List<SearchItemViewModel>>() {

            @Override
            public List<SearchItemViewModel> apply(CitiesResponse citiesResponse, List<City> favCities) throws Exception {
                Set<String> citySet = new HashSet<String>();
                for (City city : favCities) {
                    citySet.add(city.getCity());
                }
                List<SearchItemViewModel> searchItemViewModelList = new ArrayList<>();
                String[] cities = citiesResponse.getCityNameList().split("\\|");
                String[] latLongList = citiesResponse.getLatLongList().split(" ");
                for (int i = 0; i < cities.length && i < latLongList.length; i++) {
                    if (cities[i].toLowerCase().contains(searchString.toLowerCase())) {
                        String[] latLong = latLongList[i].split(",");
                        City city = new City(cities[i], latLong[0], latLong[1]);
                        boolean isFavorite = citySet.contains(cities[i]);
                        SearchItemViewModel searchItemViewModel = new SearchItemViewModel(city, isFavorite, searchItemHandler);
                        searchItemViewModelList.add(searchItemViewModel);
                    }
                }
                return searchItemViewModelList;
            }
        }).compose(RxUtil.schedulersTransformer);
    }

    public static Observable getSearchObservableForZipCode(int zipCode) {
        final ZipCodeLatLongRequest zipcodeLatLongRequest = RetrofitClient.getClient().create(ZipCodeLatLongRequest.class);
        Observable<ZipCodeLatLongResponse> zipCodeLatLongResponseObservable = zipcodeLatLongRequest.getLatLongForZipcode(zipCode);
        return zipCodeLatLongResponseObservable.compose(RxUtil.schedulersTransformer);
    }

    public static Observable getWeatherForecastDataForSingleLocation(City city) {
        final WeatherInfoRequest weatherInfoRequest = RetrofitClient.getClient().create(WeatherInfoRequest.class);
        Observable<WeatherInfoResponse> weatherInfoResponseObservable = weatherInfoRequest.getWeatherInfo(city.getLatitude(), city.getLongitude(), Constants.PRODUCT_VALUE,
                Constants.BEGIN_TIME, Constants.END_TIME, Constants.MAXT_VALUE, Constants.MINT_VALUE, Constants.TEMP_VALUE, Constants.ICONS_VALUE);
        return weatherInfoResponseObservable.flatMap(new Function<WeatherInfoResponse, ObservableSource<Map<String, DayWeatherInfo>>>() {
            @Override
            public ObservableSource<Map<String, DayWeatherInfo>> apply(WeatherInfoResponse weatherInfoResponse) throws Exception {
                Log.i(TAG, "accept: " + weatherInfoResponse);
                Map<Location, Map<String, DayWeatherInfo>> locationWeatherInfoMap = Util.convertToWeatherForecastData(weatherInfoResponse.getData());
                return Observable.just(locationWeatherInfoMap.get(weatherInfoResponse.getData().getLocationList().get(0)));
            }
        }).compose(RxUtil.schedulersTransformer);
    }

    public static Single<Boolean> updateForecastDataForFavorites(String latLongValues) {
        final MultiLocationWeatherInfoRequest multiLocationWeatherInfoRequest = RetrofitClient.getClient().create(MultiLocationWeatherInfoRequest.class);
        Observable<WeatherInfoResponse> weatherInfoResponseObservable = multiLocationWeatherInfoRequest.getWeatherInfo(latLongValues, Constants.PRODUCT_VALUE,
                Constants.BEGIN_TIME, Constants.END_TIME, Constants.MAXT_VALUE, Constants.MINT_VALUE, Constants.TEMP_VALUE, Constants.ICONS_VALUE);
        return weatherInfoResponseObservable.any(new Predicate<WeatherInfoResponse>() {
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
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable loadFavoritesDataObservable(final FavoriteItemHandler favoriteItemHandler) {
        Observable citiesObservable = DatabaseManager.getInstance().getFavoriteCities();
        return citiesObservable.flatMap(new Function<List<City>, Observable<List<FavoriteItemViewModel>>>() {
            @Override
            public Observable<List<FavoriteItemViewModel>> apply(List<City> cityList) throws Exception {
                List<FavoriteItemViewModel> favoriteItemViewModels = new ArrayList<>();
                for (City city : cityList) {
                    favoriteItemViewModels.add(new FavoriteItemViewModel(city, favoriteItemHandler));
                }
                return Observable.just(favoriteItemViewModels);
            }
        }).compose(RxUtil.schedulersTransformer);
    }


    public static Observable<String> getFavouriteCitiesLocationData() {
        Observable citiesObservable = DatabaseManager.getInstance().getFavoriteCities();
        return citiesObservable.flatMap(new Function<List<City>, Observable<String>>() {
            @Override
            public Observable<String> apply(List<City> cityList) throws Exception {
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
        }).compose(RxUtil.schedulersTransformer);
    }
}
