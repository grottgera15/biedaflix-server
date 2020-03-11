package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.SeriesStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class SeriesLightResponse implements Serializable {

    private String id;

    private String name;
    private String description;

    private Optional<MediaFilesResponse> banner;
    private Optional<MediaFilesResponse> logo;

    private String sourceId;

    private SeriesStatus status;

    public SeriesLightResponse(String id, String name, String description, MediaFilesResponse banner, MediaFilesResponse logo, String sourceId, SeriesStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.banner = Optional.of(banner);
        this.logo = Optional.of(logo);
        this.sourceId = sourceId;
        this.status = status;
    }
}
