package bestworkingconditions.biedaflix.server.vod.series.model;

import bestworkingconditions.biedaflix.server.file.FileResource;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "series")
@Getter
@Setter
@NoArgsConstructor
public class Series {

    @Id
    private String id;

    private String name;
    private String description;

    private FileResource seriesBanner;
    private FileResource logo;

    private String streamingServiceId;
    private SeriesStatus status;

    public String getFolderName(){
        return this.id;
    }
}
