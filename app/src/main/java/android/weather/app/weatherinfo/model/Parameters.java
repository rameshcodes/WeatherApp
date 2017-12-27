package android.weather.app.weatherinfo.model;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "parameters", strict = false)
public class Parameters {

    @Attribute(name = "applicable-location")
    private String applicableLocation;

    @ElementList(entry = "temperature", inline = true)
    private List<Temperature> temperatureList;

    @Element(name = "conditions-icon")
    private ConditionIcons conditionIcons;

    public String getApplicableLocation() {
        return applicableLocation;
    }

    public void setApplicableLocation(String applicableLocation) {
        this.applicableLocation = applicableLocation;
    }

    public ConditionIcons getConditionIcons() {
        return conditionIcons;
    }

    public void setConditionIcons(ConditionIcons conditionIcons) {
        this.conditionIcons = conditionIcons;
    }

    public List<Temperature> getTemperatureList() {
        return temperatureList;
    }

    public void setTemperatureList(List<Temperature> temperatureList) {
        this.temperatureList = temperatureList;
    }
}
