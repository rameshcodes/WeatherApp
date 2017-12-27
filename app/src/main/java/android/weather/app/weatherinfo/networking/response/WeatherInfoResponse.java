package android.weather.app.weatherinfo.networking.response;


import android.weather.app.weatherinfo.model.Data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "dwml", strict = false)
public class WeatherInfoResponse {
    @Element
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
