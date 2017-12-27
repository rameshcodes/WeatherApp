package android.weather.app.weatherinfo.model;

import org.simpleframework.xml.Element;

public class Location {
    @Element(name = "location-key")
    private String locationKey;

    @Element(name = "point")
    private Point point;

    public String getLocationKey() {
        return locationKey;
    }

    public void setLocationKey(String locationKey) {
        this.locationKey = locationKey;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
