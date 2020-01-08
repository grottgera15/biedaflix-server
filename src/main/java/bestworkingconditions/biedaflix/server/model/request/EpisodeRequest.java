package bestworkingconditions.biedaflix.server.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class EpisodeRequest {

    @NotNull
    @NotBlank(message = "seriesId must not be blank!")
    private String seriesId;
    @NotNull
    private int seasonNumber;
    @NotNull
    private int episodeNumber;
    @NotNull
    @NotBlank(message = "name must not be blank!")
    private String name;
    @NotNull
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
