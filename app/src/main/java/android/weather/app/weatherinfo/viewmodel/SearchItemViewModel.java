package android.weather.app.weatherinfo.viewmodel;


import android.databinding.ObservableBoolean;
import android.weather.app.weatherinfo.handler.SearchItemHandler;
import android.weather.app.weatherinfo.model.City;

public class SearchItemViewModel implements ViewModel {
    public final City city;
    public final SearchItemHandler mSearchItemHandler;
    public final ObservableBoolean isFavorite;

    public SearchItemViewModel(City city, boolean isFavorite, SearchItemHandler mSearchFragmentHandler) {
        if (mSearchFragmentHandler == null) {
            throw new RuntimeException("SearchItemHandler cannot be null");
        }
        this.city = city;
        this.isFavorite = new ObservableBoolean(isFavorite);
        this.mSearchItemHandler = mSearchFragmentHandler;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite.set(isFavorite);
    }
}
