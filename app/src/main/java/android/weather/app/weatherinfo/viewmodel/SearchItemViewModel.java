package android.weather.app.weatherinfo.viewmodel;


import android.weather.app.weatherinfo.model.City;

public class SearchItemViewModel implements ViewModel {
    public final City city;

    public SearchItemViewModel(City city) {
        this.city = city;
    }
}
