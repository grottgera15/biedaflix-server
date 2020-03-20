package bestworkingconditions.biedaflix.server.vod.series.model;

import bestworkingconditions.biedaflix.server.vod.episode.model.response.EpisodeLightResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SeriesFullResponse extends SeriesLightResponse {
    private List<EpisodeLightResponse> episodes;
}
