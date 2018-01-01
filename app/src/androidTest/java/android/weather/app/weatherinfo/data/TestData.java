package android.weather.app.weatherinfo.data;


import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.model.DayWeatherInfo;
import android.weather.app.weatherinfo.model.HourWeatherInfo;
import android.weather.app.weatherinfo.utils.Util;

public class TestData {
    public static final String CITY_NAME = "Rapid City,SD";

    public static City getCityTestData() {
        return new City(CITY_NAME, "44.05", "-103.07");
    }

    public static DayWeatherInfo getDayWeatherTestData() {
        DayWeatherInfo dayWeatherInfo = new DayWeatherInfo();
        dayWeatherInfo.setDate("Jan 01");
        dayWeatherInfo.setMinTemp("-22");
        dayWeatherInfo.setMaxTemp("7");
        dayWeatherInfo.setCityName(CITY_NAME);

        String hourlyInfo = "[{\"imageUrl\":\"http://forecast.weather.gov/images/wtf/nsct.jpg\",\"temp\":\"-22\",\"time\":\"2018-01-01T02:00:00-07:00\"},{\"imageUrl\":\"http://forecast.weather.gov/images/wtf/nfew.jpg\",\"temp\":\"-19\",\"time\":\"2018-01-01T05:00:00-07:00\"},{\"imageUrl\":\"http://forecast.weather.gov/images/wtf/few.jpg\",\"temp\":\"-18\",\"time\":\"2018-01-01T08:00:00-07:00\"},{\"imageUrl\":\"http://forecast.weather.gov/images/wtf/few.jpg\",\"temp\":\"-1\",\"time\":\"2018-01-01T11:00:00-07:00\"}]";
        dayWeatherInfo.setHourWeatherInfoList(Util.jsonElementToList(hourlyInfo, HourWeatherInfo[].class));
        return dayWeatherInfo;
    }
}
