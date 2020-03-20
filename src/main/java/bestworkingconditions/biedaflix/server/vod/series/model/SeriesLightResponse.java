package bestworkingconditions.biedaflix.server.vod.series.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.URL;

@Data
@NoArgsConstructor
public class SeriesLightResponse implements Serializable {

    private String id;

    private String name;
    private String description;

    private URL banner;
    private URL logo;

    private String sourceId;
    private SeriesStatus status;
}
