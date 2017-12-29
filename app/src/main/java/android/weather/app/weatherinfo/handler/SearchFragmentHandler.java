package android.weather.app.weatherinfo.handler;


import android.weather.app.weatherinfo.model.City;

public interface SearchFragmentHandler {

    void onItemClicked(City city);
    void onFavorite(City city);
}
