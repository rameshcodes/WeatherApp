package android.weather.app.weatherinfo.viewmodel.factory;


import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.viewmodel.WeatherInfoActivityViewModel;

public class WeatherInfoViewModelFactory implements ViewModelProvider.Factory {
    private final City city;

    public WeatherInfoViewModelFactory(City city) {
        this.city = city;
    }

    @NonNull
    @Override
    public WeatherInfoActivityViewModel create(@NonNull Class modelClass) {
        return new WeatherInfoActivityViewModel(city);
    }
}
