package bestworkingconditions.biedaflix.server.vod.series.model;

import bestworkingconditions.biedaflix.server.file.FileResource;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "series")
@Data
@AllArgsConstructor
public class Series {

    @Id
    private String id;

    private String name;
    private String description;

    private FileResource banner;
    private FileResource logo;

    private String sourceId;
    private SeriesStatus status;
}
