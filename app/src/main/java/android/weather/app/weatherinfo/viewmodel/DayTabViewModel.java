package android.weather.app.weatherinfo.viewmodel;


import android.databinding.ObservableBoolean;
import android.weather.app.weatherinfo.handler.DayListHandler;

public class DayTabViewModel implements AppViewModel {
    public final ObservableBoolean isSelected = new ObservableBoolean(false);
    public final String day;
    public final DayListHandler dayListHandler;
    private final int position;


    public DayTabViewModel(String day, int position, DayListHandler dayListHandler) {
        this.day = day;
        this.position = position;
        this.dayListHandler = dayListHandler;
    }

    public void showDayWeather() {
        dayListHandler.onDaySelected(position);
    }

    public void setSelected(boolean isSelected) {
        this.isSelected.set(isSelected);
    }
}
