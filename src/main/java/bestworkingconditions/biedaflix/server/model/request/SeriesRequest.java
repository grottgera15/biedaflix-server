package bestworkingconditions.biedaflix.server.model.request;

import bestworkingconditions.biedaflix.server.model.SeriesStatus;

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
    private SeriesStatus status;

    public SeriesRequest() {
    }

    public SeriesRequest(@NotBlank String name, @NotBlank String description, @NotBlank String sourceId, @NotNull SeriesStatus status) {
        this.name = name;
        this.description = description;
        this.sourceId = sourceId;
        this.status = status;
    }

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

    public SeriesStatus getStatus() {
        return status;
    }

    public void setStatus(SeriesStatus status) {
        this.status = status;
    }
}
