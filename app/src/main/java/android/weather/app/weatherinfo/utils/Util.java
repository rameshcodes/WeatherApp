package android.weather.app.weatherinfo.utils;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;
import android.weather.app.weatherinfo.model.Data;
import android.weather.app.weatherinfo.model.DayWeatherInfo;
import android.weather.app.weatherinfo.model.HourWeatherInfo;
import android.weather.app.weatherinfo.model.Location;
import android.weather.app.weatherinfo.model.Parameters;
import android.weather.app.weatherinfo.model.Temperature;
import android.weather.app.weatherinfo.model.TimeLayout;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
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

    public static <T> List<T> jsonElementToList(String data, Class<T[]> clazz) {
        if (data == null)
            return null;
        return Arrays.asList(new Gson().fromJson(data, clazz));
    }

    public static boolean hideSoftKeyboard(Context context, IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(windowToken, 0);
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

    public static Map<Location, Map<String, DayWeatherInfo>> convertToWeatherForecastData(Data data) {
        Map<Location, Map<String, DayWeatherInfo>> parametersMapMap = new LinkedHashMap<>();
        for (Parameters parameter : data.getParameters()) {
            Map<String, DayWeatherInfo> dayWeatherInfoMap = new TreeMap<>();
            for (Temperature temperature : parameter.getTemperatureList()) {
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
                        HourWeatherInfo hourWeatherInfo = new HourWeatherInfo(parameter.getConditionIcons().getIconLink().get(i), timeLayout.getStartTime().get(i), temperature.getValue().get(i));
                        dayWeatherInfo.addHourlyWeather(hourWeatherInfo);
                    }
                    if (!dayWeatherInfoMap.containsKey(date)) {
                        dayWeatherInfo.setDate(date);
                        dayWeatherInfoMap.put(date, dayWeatherInfo);
                    }
                }
            }
            parametersMapMap.put(getLocationForPoint(parameter.getApplicableLocation(), data.getLocationList()), dayWeatherInfoMap);
        }
        return parametersMapMap;
    }

    public static Map<String, DayWeatherInfo> convertWeatherDataToMap(List<DayWeatherInfo> dayWeatherInfoList) {
        Map<String, DayWeatherInfo> dayWeatherInfoMap = new TreeMap<>();
        for (DayWeatherInfo dayWeatherInfo : dayWeatherInfoList) {
            dayWeatherInfoMap.put(dayWeatherInfo.getDate(), dayWeatherInfo);
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

    public static String getDate(String dateTime) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            date = simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd", Locale.US);
        return simpleDateFormat.format(date);
    }

    private static Location getLocationForPoint(String point, List<Location> locationList) {
        for (Location location : locationList) {
            if (point.equals(location.getLocationKey())) {
                return location;
            }
        }
        return null;
    }
}
