package android.weather.app.weatherinfo.handler;


import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.viewmodel.SearchItemViewModel;

public interface SearchItemHandler {

    void onItemClicked(City city);

    void onFavorite(SearchItemViewModel searchItemViewModel);
}
