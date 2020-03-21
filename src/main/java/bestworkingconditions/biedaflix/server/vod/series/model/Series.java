package bestworkingconditions.biedaflix.server.vod.series.model;

import bestworkingconditions.biedaflix.server.file.FileResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document(collection = "series")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Series {

    @Id
    private String id;

    @NotBlank
    private String name;
    private String description;

    private FileResource banner;
    private FileResource logo;

    @NotBlank
    private String sourceId;
    private SeriesStatus status;
}
