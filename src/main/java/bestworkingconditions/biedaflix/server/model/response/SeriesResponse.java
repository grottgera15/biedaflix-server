package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.Episode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeriesResponse {

    private String id;

    private String name;
    private String description;

    private MediaFilesResponse banner;
    private MediaFilesResponse logo;

    private String streamingServiceId;
    private Boolean onGoing;

    private Map<Integer,List<EpisodeResponse>> seasons = new HashMap<>();

    public SeriesResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MediaFilesResponse getBanner() {
        return banner;
    }

    public void setBanner(MediaFilesResponse banner) {
        this.banner = banner;
    }

    public MediaFilesResponse getLogo() {
        return logo;
    }

    public void setLogo(MediaFilesResponse logo) {
        this.logo = logo;
    }

    public String getStreamingServiceId() {
        return streamingServiceId;
    }

    public void setStreamingServiceId(String streamingServiceId) {
        this.streamingServiceId = streamingServiceId;
    }

    public Boolean getOnGoing() {
        return onGoing;
    }

    public void setOnGoing(Boolean onGoing) {
        this.onGoing = onGoing;
    }

    public Map<Integer, List<EpisodeResponse>> getSeasons() {
        return seasons;
    }

    public void setSeasons(Map<Integer, List<EpisodeResponse>> seasons) {
        this.seasons = seasons;
    }
}
