package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.SeriesStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeriesFullResponse extends SeriesLightResponse {

    private Map<Integer,List<EpisodeLightResponse>> seasons = new HashMap<>();

    public SeriesFullResponse() {
    }

    public SeriesFullResponse(String id, String name, String description, MediaFilesResponse banner, MediaFilesResponse logo, String sourceId, SeriesStatus status, Map<Integer, List<EpisodeLightResponse>> seasons) {
        super(id, name, description, banner, logo, sourceId, status);
        this.seasons = seasons;
    }

    public Map<Integer, List<EpisodeLightResponse>> getSeasons() {
        return seasons;
    }

    public void setSeasons(Map<Integer, List<EpisodeLightResponse>> seasons) {
        this.seasons = seasons;
    }
}
