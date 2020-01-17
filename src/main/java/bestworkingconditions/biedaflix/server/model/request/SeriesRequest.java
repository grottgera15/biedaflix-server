package bestworkingconditions.biedaflix.server.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SeriesRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String sourceId;
    @NotNull
    private Boolean onGoing;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Boolean getOnGoing() {
        return onGoing;
    }

    public void setOnGoing(Boolean onGoing) {
        this.onGoing = onGoing;
    }
}
