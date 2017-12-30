package android.weather.app.weatherinfo.model;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class Data {

    @ElementList(entry ="location",inline = true)
    private List<Location> locationList;

    @ElementList(entry = "time-layout", inline = true)
    private List<TimeLayout> timeLayouts;

    @ElementList(entry = "parameters", inline = true)
    private List<Parameters> parameters;

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public List<Parameters> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameters> parameters) {
        this.parameters = parameters;
    }

    public List<TimeLayout> getTimeLayouts() {
        return timeLayouts;
    }

    public void setTimeLayouts(List<TimeLayout> timeLayouts) {
        this.timeLayouts = timeLayouts;
    }
}
