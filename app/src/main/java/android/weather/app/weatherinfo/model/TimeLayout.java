package android.weather.app.weatherinfo.model;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "time-layout", strict = false)
public class TimeLayout {

    @Element(name = "layout-key")
    private String layoutKey;

    @ElementList(entry = "start-valid-time", inline = true)
    private List<String> startTime;

    @ElementList(entry = "end-valid-time", inline = true,required = false)
    private List<String> endTime;

    public String getLayoutKey() {
        return layoutKey;
    }

    public void setLayoutKey(String layoutKey) {
        this.layoutKey = layoutKey;
    }

    public List<String> getStartTime() {
        return startTime;
    }

    public void setStartTime(List<String> startTime) {
        this.startTime = startTime;
    }

    public List<String> getEndTime() {
        return endTime;
    }

    public void setEndTime(List<String> endTime) {
        this.endTime = endTime;
    }
}
