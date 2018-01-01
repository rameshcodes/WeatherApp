package android.weather.app.weatherinfo;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.weather.app.weatherinfo.model.City;
import android.weather.app.weatherinfo.model.DayWeatherInfo;
import android.weather.app.weatherinfo.persistance.WeatherDatabase;
import android.weather.app.weatherinfo.persistance.dao.CityDao;
import android.weather.app.weatherinfo.persistance.dao.DayWeatherDao;
import android.weather.app.weatherinfo.data.TestData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private WeatherDatabase mDb;
    private CityDao cityDao;
    private DayWeatherDao dayWeatherDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, WeatherDatabase.class).build();
        cityDao = mDb.getCityDao();
        dayWeatherDao = mDb.getDayWeatherDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeAndReadCity() {
        City city = TestData.getCityTestData();
        cityDao.insert(city);
        List<City> cities = cityDao.getAllCities();
        assertThat(cities.size(), equalTo(1));
        assertThat(cities.get(0).getCity(), equalTo(city.getCity()));
    }

    @Test
    public void clearCityTableData() {
        cityDao.delete();
        List<City> cities = cityDao.getAllCities();
        assertEquals(cities.size(), 0);
    }

    @Test
    public void insertAndTestDayWeatherData() {
        cityDao.insert(TestData.getCityTestData());
        DayWeatherInfo dayWeatherInfo = TestData.getDayWeatherTestData();
        dayWeatherDao.insert(dayWeatherInfo);
        List<DayWeatherInfo> dayWeatherInfoList = dayWeatherDao.getWeatherForCity(TestData.CITY_NAME);
        assertEquals(dayWeatherInfoList.size(), 1);
    }

    @Test
    public void deleteWeatherInfoData() {
        dayWeatherDao.deleteCityWeatherData(TestData.CITY_NAME);
        List<DayWeatherInfo> dayWeatherInfoList = dayWeatherDao.getWeatherForCity(TestData.CITY_NAME);
        assertEquals(dayWeatherInfoList.size(), 0);
    }

}
