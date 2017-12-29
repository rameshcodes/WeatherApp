package android.weather.app.weatherinfo.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DayWeatherInfo implements Comparable<DayWeatherInfo>,Parcelable {
    private String date;
    private String minTemp;
    private String maxTemp;
    private List<HourWeatherInfo> hourWeatherInfoList = new ArrayList<>();

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
        this.hourWeatherInfoList.add(hourWeatherInfo);
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
        dest.writeString(this.date);
        dest.writeString(this.minTemp);
        dest.writeString(this.maxTemp);
        dest.writeList(this.hourWeatherInfoList);
    }

    public DayWeatherInfo() {
    }

    protected DayWeatherInfo(Parcel in) {
        this.date = in.readString();
        this.minTemp = in.readString();
        this.maxTemp = in.readString();
        this.hourWeatherInfoList = new ArrayList<HourWeatherInfo>();
        in.readList(this.hourWeatherInfoList, HourWeatherInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<DayWeatherInfo> CREATOR = new Parcelable.Creator<DayWeatherInfo>() {
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
