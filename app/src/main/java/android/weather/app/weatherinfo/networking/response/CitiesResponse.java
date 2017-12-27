package android.weather.app.weatherinfo.networking.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "dwml",strict = false)
public class CitiesResponse {
    @Element(name = "latLonList")
    private String latLongList;

    @Element(name = "cityNameList")
    private String cityNameList;

    public String getLatLongList() {
        return latLongList;
    }

    public void setLatLongList(String latLongList) {
        this.latLongList = latLongList;
    }

    public String getCityNameList() {
        return cityNameList;
    }

    public void setCityNameList(String cityNameList) {
        this.cityNameList = cityNameList;
    }
}
