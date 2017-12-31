package android.weather.app.weatherinfo.viewmodel;


import android.weather.app.weatherinfo.handler.FavoriteItemHandler;
import android.weather.app.weatherinfo.model.City;

public class FavoriteItemViewModel implements AppViewModel {
    public final City city;
    public final FavoriteItemHandler favoriteItemHandler;

    public FavoriteItemViewModel(City city, FavoriteItemHandler favoriteItemHandler) {
        this.city = city;
        this.favoriteItemHandler = favoriteItemHandler;
    }
}
