package android.weather.app.weatherinfo.viewmodel;


import android.weather.app.weatherinfo.handler.SearchItemHandler;
import android.weather.app.weatherinfo.model.City;

public class SearchItemViewModel implements ViewModel {
    public final City city;
    public final SearchItemHandler mSearchItemHandler;

    public SearchItemViewModel(City city, SearchItemHandler mSearchFragmentHandler) {
        if (mSearchFragmentHandler == null) {
            throw new RuntimeException("SearchItemHandler cannot be null");
        }
        this.city = city;
        this.mSearchItemHandler = mSearchFragmentHandler;
    }
}
