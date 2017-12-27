package android.weather.app.weatherinfo.model;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "conditions-icon",strict = false)
public class ConditionIcons {
    @Attribute(name = "time-layout")
    private String timeLayout;

    @Attribute
    private String type;

    @Element
    private String name;

    @ElementList(entry = "icon-link",inline = true)
    private List<String> iconLink;

    public String getTimeLayout() {
        return timeLayout;
    }

    public void setTimeLayout(String timeLayout) {
        this.timeLayout = timeLayout;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIconLink() {
        return iconLink;
    }

    public void setIconLink(List<String> iconLink) {
        this.iconLink = iconLink;
    }
}
