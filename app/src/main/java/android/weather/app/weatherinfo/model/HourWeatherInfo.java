package android.weather.app.weatherinfo.model;


public class HourWeatherInfo {
    private final String imageUrl;
    private final String time;
    private final String temp;

    public HourWeatherInfo(String imageUrl, String time, String temp) {
        this.imageUrl = imageUrl;
        this.time = time;
        this.temp = temp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTime() {
        return time;
    }

    public String getTemp() {
        return temp;
    }
}
