package android.weather.app.weatherinfo.model;


import org.simpleframework.xml.Attribute;

public class Point {
    @Attribute(name = "latitude")
    private String longitude;

    @Attribute(name = "longitude")
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
