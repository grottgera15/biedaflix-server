package bestworkingconditions.biedaflix.server.vod.series.model;

import bestworkingconditions.biedaflix.server.vod.episode.model.response.EpisodeLightResponse;
import bestworkingconditions.biedaflix.server.common.model.response.MediaFilesResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class SeriesFullResponse extends SeriesLightResponse {

    private Map<Integer,List<EpisodeLightResponse>> seasons = new HashMap<>();

    public SeriesFullResponse(String id, String name, String description, MediaFilesResponse banner, MediaFilesResponse logo, String sourceId, SeriesStatus status, Map<Integer, List<EpisodeLightResponse>> seasons) {
        super(id, name, description, banner, logo, sourceId, status);
        this.seasons = seasons;
    }
}
