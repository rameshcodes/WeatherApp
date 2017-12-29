package android.weather.app.weatherinfo.viewmodel;


import android.databinding.ObservableBoolean;

public class DayTabViewModel implements ViewModel {
    public ObservableBoolean isSelected = new ObservableBoolean(false);
    public String day = " 29 DEC";

    public void showDayWeather() {

    }

    public void setSelected(boolean isSelected) {
        this.isSelected.set(isSelected);
    }
}
