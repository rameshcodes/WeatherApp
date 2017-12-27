package android.weather.app.weatherinfo.model;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "temperature")
public class Temperature {

    @Attribute(name = "time-layout")
    private String timeLayout;

    @Element
    private String name;

    @ElementList(entry = "value", inline = true)
    private List<String> value;

    @Attribute
    private String type;

    @Attribute
    private String units;

    public String getTimeLayout() {
        return timeLayout;
    }

    public void setTimeLayout(String timeLayout) {
        this.timeLayout = timeLayout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
