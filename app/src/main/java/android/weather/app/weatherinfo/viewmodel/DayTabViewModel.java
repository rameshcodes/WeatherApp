package android.weather.app.weatherinfo.viewmodel;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

public class DayTabViewModel implements ViewModel {
    public ObservableBoolean isSelected = new ObservableBoolean(false);
    public ObservableField<String> day = new ObservableField<String>("MAR 31");

    public DayTabViewModel(String day) {
        this.day.set(day);
    }

    public void showDayWeather() {

    }

    public void setSelected(boolean isSelected) {
        this.isSelected.set(isSelected);
    }
}
