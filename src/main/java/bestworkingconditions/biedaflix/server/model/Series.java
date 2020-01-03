package bestworkingconditions.biedaflix.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "series")
public class Series {

    @Id
    private String id;

    private String name;
    private String description;
    private String bannerVideo;
    private String logo;
    private String source;
    private Boolean onGoing;
    private List<Season> seasons;

    public Series() {
        seasons = new ArrayList<>();
    }

    public Series(String name, String description, Boolean onGoing) {
        this.name = name;
        this.description = description;
        this.onGoing = onGoing;

        seasons = new ArrayList<>();
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

    public String getBannerVideo() {
        return bannerVideo;
    }

    public void setBannerVideo(String bannerVideo) {
        this.bannerVideo = bannerVideo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
