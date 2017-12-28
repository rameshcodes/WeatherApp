package android.weather.app.weatherinfo.model;


import java.util.List;

public class DayWeatherInfo {
    private final String date;
    private final String minTemp;
    private final String maxTemp;
    private final List<HourWeatherInfo> hourWeatherInfoList;

    public DayWeatherInfo(String date, String minTemp, String maxTemp, List<HourWeatherInfo> hourWeatherInfoList) {
        this.date = date;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.hourWeatherInfoList = hourWeatherInfoList;
    }

    public String getDate() {
        return date;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public List<HourWeatherInfo> getHourWeatherInfoList() {
        return hourWeatherInfoList;
    }
}
