package android.weather.app.weatherinfo.model;


import android.os.Parcel;
import android.os.Parcelable;

public class HourWeatherInfo implements Parcelable {
    private final String imageUrl;
    private final String time;
    private final String temp;

    public HourWeatherInfo(String imageUrl, String time, String temp) {
        this.imageUrl = imageUrl;
        this.time = time;
        this.temp = temp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTime() {
        return time;
    }

    public String getTemp() {
        return temp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
        dest.writeString(this.time);
        dest.writeString(this.temp);
    }

    protected HourWeatherInfo(Parcel in) {
        this.imageUrl = in.readString();
        this.time = in.readString();
        this.temp = in.readString();
    }

    public static final Parcelable.Creator<HourWeatherInfo> CREATOR = new Parcelable.Creator<HourWeatherInfo>() {
        @Override
        public HourWeatherInfo createFromParcel(Parcel source) {
            return new HourWeatherInfo(source);
        }

        @Override
        public HourWeatherInfo[] newArray(int size) {
            return new HourWeatherInfo[size];
        }
    };
}
