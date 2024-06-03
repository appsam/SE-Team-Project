package software.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OMDBMovie {
    @JsonProperty("Title")
    private String title;

    @JsonProperty("Plot")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
