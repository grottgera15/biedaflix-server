package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.Season;

import java.util.List;

public class SeriesResponse {

    private String id;

    private  String name;
    private String description;

    private MediaFilesResponse banner;
    private MediaFilesResponse logo;

    private String streamingServiceId;
    private Boolean onGoing;
    private List<Season> seasons;

    public SeriesResponse(String id, String name, String description, MediaFilesResponse banner, MediaFilesResponse logo, String streamingServiceId, Boolean onGoing, List<Season> seasons) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.banner = banner;
        this.logo = logo;
        this.streamingServiceId = streamingServiceId;
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

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }
}
