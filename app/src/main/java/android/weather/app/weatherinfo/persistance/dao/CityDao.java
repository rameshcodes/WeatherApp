package android.weather.app.weatherinfo.persistance.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.weather.app.weatherinfo.model.City;

import java.util.List;

@Dao
public interface CityDao {

    @Query("SELECT * FROM city")
    List<City> getAllCities();

    @Query("SELECT * FROM city WHERE latitude=:lat AND longitude=:lon")
    List<City> getCityForLatLong(final String lat,final String lon);

    @Insert
    void insert(City... repos);

    @Update
    void update(City... repos);

    @Delete
    void delete(City... repos);
}
