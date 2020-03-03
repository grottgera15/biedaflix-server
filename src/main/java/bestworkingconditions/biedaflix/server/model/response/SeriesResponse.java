package bestworkingconditions.biedaflix.server.model.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeriesResponse {

    private String id;

    private String name;
    private String description;

    private MediaFilesResponse banner;
    private MediaFilesResponse logo;

    private String sourceId;
    private Boolean onGoing;

    private Map<Integer,List<EpisodeLightResponse>> seasons = new HashMap<>();

    public SeriesResponse() {
    }

    public SeriesResponse(String id, String name, String description, MediaFilesResponse banner, MediaFilesResponse logo, String sourceId, Boolean onGoing, Map<Integer, List<EpisodeLightResponse>> seasons) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.banner = banner;
        this.logo = logo;
        this.sourceId = sourceId;
        this.onGoing = onGoing;
        this.seasons = seasons;
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

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Boolean getOnGoing() {
        return onGoing;
    }

    public void setOnGoing(Boolean onGoing) {
        this.onGoing = onGoing;
    }

    public Map<Integer, List<EpisodeLightResponse>> getSeasons() {
        return seasons;
    }

    public void setSeasons(Map<Integer, List<EpisodeLightResponse>> seasons) {
        this.seasons = seasons;
    }
}
