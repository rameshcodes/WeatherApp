package android.weather.app.weatherinfo.handler;


import android.weather.app.weatherinfo.model.City;

public interface SearchItemHandler {

    void onItemClicked(City city);
    void onFavorite(City city);
}
