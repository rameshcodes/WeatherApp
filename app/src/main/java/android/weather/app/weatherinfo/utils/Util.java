package android.weather.app.weatherinfo.utils;

import android.weather.app.weatherinfo.model.Data;
import android.weather.app.weatherinfo.model.DayWeatherInfo;
import android.weather.app.weatherinfo.model.HourWeatherInfo;
import android.weather.app.weatherinfo.model.Temperature;
import android.weather.app.weatherinfo.model.TimeLayout;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

public class Util {

    public static <T> void checkNotNull(T value, String name) {
        if (value == null) {
            throw new NullPointerException(name + " should not be null");
        }
    }

    public static <T> List<T> jsonElementToList(JsonElement element, Class<T[]> clazz) {
        if (element instanceof JsonNull)
            return null;
        return Arrays.asList(new Gson().fromJson(element, clazz));
    }

    public static Map<String, DayWeatherInfo> convertToWeatherForecastData(Data data) {
        Map<String, DayWeatherInfo> dayWeatherInfoMap = new TreeMap<>();
        for (Temperature temperature : data.getParameters().getTemperatureList()) {
            TimeLayout timeLayout = getTimeLayout(temperature.getTimeLayout(), data.getTimeLayouts());
            for (int i = 0; i < timeLayout.getStartTime().size(); i++) {
                String date = getDate(timeLayout.getStartTime().get(i));
                DayWeatherInfo dayWeatherInfo = dayWeatherInfoMap.get(date);
                if (dayWeatherInfo == null) {
                    dayWeatherInfo = new DayWeatherInfo();
                }
                if (temperature.getType().equals(Constants.DAILY_MAX_TYPE)) {
                    dayWeatherInfo.setMaxTemp(temperature.getValue().get(i));
                } else if (temperature.getType().equals(Constants.DAILY_MIN_TYPE)) {
                    dayWeatherInfo.setMinTemp(temperature.getValue().get(i));
                } else if (temperature.getType().equals(Constants.DAILY_HOURLY_TYPE)) {
                    HourWeatherInfo hourWeatherInfo = new HourWeatherInfo(data.getParameters().getConditionIcons().getIconLink().get(i), timeLayout.getStartTime().get(i), temperature.getValue().get(i));
                    dayWeatherInfo.addHourlyWeather(hourWeatherInfo);
                }
                if (!dayWeatherInfoMap.containsKey(date)) {
                    dayWeatherInfo.setDate(date);
                    dayWeatherInfoMap.put(date, dayWeatherInfo);
                }
            }
        }
        return dayWeatherInfoMap;
    }

    private static TimeLayout getTimeLayout(String key, List<TimeLayout> timeLayoutList) {
        for (TimeLayout timeLayout : timeLayoutList) {
            if (key.equals(timeLayout.getLayoutKey())) {
                return timeLayout;
            }
        }
        return null;
    }

    private static String getDate(String dateTime) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            date = simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd", Locale.US);
        return simpleDateFormat.format(date);
    }

    public static String getTime(String dateTime) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            date = simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        return simpleDateFormat.format(date);
    }
}
