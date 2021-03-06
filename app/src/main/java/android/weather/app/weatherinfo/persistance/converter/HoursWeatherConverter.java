package android.weather.app.weatherinfo.persistance.converter;


import android.arch.persistence.room.TypeConverter;
import android.weather.app.weatherinfo.model.HourWeatherInfo;
import android.weather.app.weatherinfo.utils.Util;

import com.google.gson.Gson;

import java.util.List;

public class HoursWeatherConverter {
    @TypeConverter
    public static String fromHoursDataList(List<HourWeatherInfo> hourWeatherInfoList) {
        if (hourWeatherInfoList == null) {
            return (null);
        }
        Gson gson = new Gson();
        String json = gson.toJson(hourWeatherInfoList);
        return json;
    }

    @TypeConverter
    public static List<HourWeatherInfo> toHourDataList(String data) {
        if (data == null) {
            return (null);
        }
        return Util.jsonElementToList(data, HourWeatherInfo[].class);
    }

}
