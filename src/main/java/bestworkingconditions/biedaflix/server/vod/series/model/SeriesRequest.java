package bestworkingconditions.biedaflix.server.vod.series.model;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SeriesRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String sourceId;
    @NotNull
    private SeriesStatus status;
}
