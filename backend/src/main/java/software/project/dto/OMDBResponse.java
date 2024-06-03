package software.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OMDBResponse {
    @JsonProperty("Search")
    private List<OMDBMovie> search;

    public List<OMDBMovie> getSearch() {
        return search;
    }

    public void setSearch(List<OMDBMovie> search) {
        this.search = search;
    }
}
