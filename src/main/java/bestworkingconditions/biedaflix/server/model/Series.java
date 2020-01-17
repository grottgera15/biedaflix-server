package bestworkingconditions.biedaflix.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "series")
public class Series {

    @Id
    private String id;

    private String name;
    private String description;

    private SeriesBanner bannerVideo;
    private SeriesLogo logo;

    private String streamingServiceId;
    private Boolean onGoing;
    private List<Season> seasons;

    public Series() {
        seasons = new ArrayList<>();
    }

    public Series(String id, String name, String description, SeriesBanner bannerVideo, SeriesLogo logo, String streamingServiceId, Boolean onGoing, List<Season> seasons) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.bannerVideo = bannerVideo;
        this.logo = logo;
        this.streamingServiceId = streamingServiceId;
        this.onGoing = onGoing;
        this.seasons = seasons;
    }

    public String getId() {
        return id;
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

    public SeriesBanner getBannerVideo() {
        return bannerVideo;
    }

    public void setBannerVideo(SeriesBanner bannerVideo) {
        this.bannerVideo = bannerVideo;
    }

    public SeriesLogo getLogo() {
        return logo;
    }

    public void setLogo(SeriesLogo logo) {
        this.logo = logo;
    }

    public void setId(String id) {
        this.id = id;
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
