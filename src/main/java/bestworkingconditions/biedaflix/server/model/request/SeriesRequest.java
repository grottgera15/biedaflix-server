package bestworkingconditions.biedaflix.server.model.request;

import bestworkingconditions.biedaflix.server.model.SeriesStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class SeriesRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String sourceId;

    @NotNull
    private SeriesStatus status;

    public SeriesRequest(@NotBlank String name, @NotBlank String description, @NotBlank String sourceId, @NotNull SeriesStatus status) {
        this.name = name;
        this.description = description;
        this.sourceId = sourceId;
        this.status = status;
    }
}
