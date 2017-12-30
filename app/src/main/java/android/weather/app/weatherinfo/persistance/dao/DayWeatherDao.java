package android.weather.app.weatherinfo.persistance.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.weather.app.weatherinfo.model.DayWeatherInfo;

import java.util.List;

@Dao
public interface DayWeatherDao {

    @Query("SELECT * FROM dayweatherinfo")
    List<DayWeatherInfo> getAllCities();

    @Query("SELECT * FROM DayWeatherInfo WHERE cityName=:cityName")
    List<DayWeatherInfo> getWeatherForCity(final String cityName);

    @Query("DELETE FROM DAYWEATHERINFO WHERE cityName=:cityName")
    void deleteCityWeatherData(final String cityName);

    @Insert
    void insert(DayWeatherInfo... repos);

    @Update
    void update(DayWeatherInfo... repos);

    @Delete
    void delete(DayWeatherInfo... repos);
}
