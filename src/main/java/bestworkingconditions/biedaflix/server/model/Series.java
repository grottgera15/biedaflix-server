package bestworkingconditions.biedaflix.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "series")
@Getter
@Setter
@NoArgsConstructor
public class Series {

    @Id
    private String id;

    private String name;
    private String description;

    private SeriesBanner seriesBanner;
    private SeriesLogo logo;

    private String streamingServiceId;
    private SeriesStatus status;

    public String getFolderName(){
        return this.id;
    }
}
