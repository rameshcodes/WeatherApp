package android.weather.app.weatherinfo.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.weather.app.weatherinfo.persistance.converter.HoursWeatherConverter;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = City.class, parentColumns = "city",
        childColumns = "cityName",onDelete = CASCADE))
public class DayWeatherInfo implements Comparable<DayWeatherInfo>, Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String date;
    private String minTemp;
    private String maxTemp;
    @TypeConverters(HoursWeatherConverter.class)
    private List<HourWeatherInfo> hourWeatherInfoList;
    private String cityName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setHourWeatherInfoList(List<HourWeatherInfo> hourWeatherInfoList) {
        this.hourWeatherInfoList = hourWeatherInfoList;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public void addHourlyWeather(HourWeatherInfo hourWeatherInfo) {
        if (hourWeatherInfoList == null) {
            hourWeatherInfoList = new ArrayList<>();
        }
        this.hourWeatherInfoList.add(hourWeatherInfo);
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public int compareTo(@NonNull DayWeatherInfo o) {
        return this.date.compareTo(o.date);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.date);
        dest.writeString(this.minTemp);
        dest.writeString(this.maxTemp);
        dest.writeTypedList(this.hourWeatherInfoList);
        dest.writeString(this.cityName);
    }

    public DayWeatherInfo() {
    }

    protected DayWeatherInfo(Parcel in) {
        this.id = in.readInt();
        this.date = in.readString();
        this.minTemp = in.readString();
        this.maxTemp = in.readString();
        this.hourWeatherInfoList = in.createTypedArrayList(HourWeatherInfo.CREATOR);
        this.cityName = in.readString();
    }

    public static final Creator<DayWeatherInfo> CREATOR = new Creator<DayWeatherInfo>() {
        @Override
        public DayWeatherInfo createFromParcel(Parcel source) {
            return new DayWeatherInfo(source);
        }

        @Override
        public DayWeatherInfo[] newArray(int size) {
            return new DayWeatherInfo[size];
        }
    };
}
