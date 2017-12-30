package android.weather.app.weatherinfo.viewmodel.factory;


import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.viewmodel.WeatherInfoActivityViewModel;

public class WeatherInfoViewModelFactory implements ViewModelProvider.Factory {
    private final City city;
    private final boolean isFromFavoriteScreen;

    public WeatherInfoViewModelFactory(City city, boolean isFromFavoriteScreen) {
        this.city = city;
        this.isFromFavoriteScreen = isFromFavoriteScreen;
    }

    @NonNull
    @Override
    public WeatherInfoActivityViewModel create(@NonNull Class modelClass) {
        return new WeatherInfoActivityViewModel(city, isFromFavoriteScreen);
    }
}
