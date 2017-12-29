package android.weather.app.weatherinfo.viewmodel;


import android.weather.app.weatherinfo.handler.FavoriteHandler;
import android.weather.app.weatherinfo.model.City;

public class FavoriteItemViewModel implements ViewModel {
    public final City city;
    public final FavoriteHandler favoriteHandler;

    public FavoriteItemViewModel(City city, FavoriteHandler favoriteHandler) {
        this.city = city;
        this.favoriteHandler = favoriteHandler;
    }
}
