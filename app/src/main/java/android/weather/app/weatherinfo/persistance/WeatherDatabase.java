package android.weather.app.weatherinfo.persistance;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.model.DayWeatherInfo;
import android.weather.app.weatherinfo.persistance.dao.CityDao;
import android.weather.app.weatherinfo.persistance.dao.DayWeatherDao;

@Database(entities = {City.class, DayWeatherInfo.class}, version = 1,exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {
    private static final String DB_NAME = "weather.db";
    private static volatile WeatherDatabase instance;

    public static synchronized WeatherDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static WeatherDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                WeatherDatabase.class,
                DB_NAME).build();
    }

    public abstract CityDao getCityDao();

    public abstract DayWeatherDao getDayWeatherDao();

}
