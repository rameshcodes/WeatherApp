package android.weather.app.weatherinfo.networking.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "dwml", strict = false)
public class ZipCodeLatLongResponse {
    @Element(name = "latLonList",required = false)
    private String latLongList;

    public String getLatLongList() {
        return latLongList;
    }

    public void setLatLongList(String latLongList) {
        this.latLongList = latLongList;
    }
}
