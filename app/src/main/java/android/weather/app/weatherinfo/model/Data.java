package android.weather.app.weatherinfo.model;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(strict = false)
public class Data {

    @Element
    private Location location;

    @ElementList(entry = "time-layout", inline = true)
    private List<TimeLayout> timeLayouts;

    @Element
    private Parameters parameters;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public List<TimeLayout> getTimeLayouts() {
        return timeLayouts;
    }

    public void setTimeLayouts(List<TimeLayout> timeLayouts) {
        this.timeLayouts = timeLayouts;
    }
}
