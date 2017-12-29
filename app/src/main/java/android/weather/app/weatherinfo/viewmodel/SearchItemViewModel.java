package android.weather.app.weatherinfo.viewmodel;


import android.weather.app.weatherinfo.handler.SearchFragmentHandler;
import android.weather.app.weatherinfo.model.City;

public class SearchItemViewModel implements ViewModel {
    public final City city;
    public final SearchFragmentHandler mSearchFragmentHandler;

    public SearchItemViewModel(City city, SearchFragmentHandler mSearchFragmentHandler) {
        if (mSearchFragmentHandler == null) {
            throw new RuntimeException("SearchFragmentHandler cannot be null");
        }
        this.city = city;
        this.mSearchFragmentHandler = mSearchFragmentHandler;
    }
}
