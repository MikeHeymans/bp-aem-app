package be.hogent.aem.core.models;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class Category {
    private Long id;
    private Map<String, String> name;
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getName() {
        return name;
    }

    public void setName(Map<String, String> name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName(String language) {
        String output = StringUtils.EMPTY;
        if (name != null && name.containsKey(language)) {
            output = name.get(language);
        }
        return output;
    }
}
