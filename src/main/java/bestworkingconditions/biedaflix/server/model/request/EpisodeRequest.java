package bestworkingconditions.biedaflix.server.model.request;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class EpisodeRequest {

    @NotBlank(message = "seriesId must not be blank!")
    private String seriesId;
    @NotBlank(message = "seasonNumber must not be blank!")
    private int seasonNumber;
    @NotBlank(message = "episodeNumber must not be blank!")
    private int episodeNumber;
    @NotBlank(message = "name must not be blank!")
    private String name;

    private Date releaseDate;

    public EpisodeRequest() {
    }

    public EpisodeRequest(String seriesId, int seasonNumber, int episodeNumber, String name, Date releaseDate) {
        this.seriesId = seriesId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public String getName() {
        return name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }
}
