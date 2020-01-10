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
    @NotBlank(message = "magnetLink must not be blank!")
    private String magnetLink;

    public EpisodeRequest() {
    }

    public EpisodeRequest(@NotNull @NotBlank(message = "seriesId must not be blank!") String seriesId, @NotNull int seasonNumber, @NotNull int episodeNumber, @NotNull @NotBlank(message = "name must not be blank!") String name, @NotNull Date releaseDate, @NotBlank(message = "magnetLink must not be blank!") String magnetLink) {
        this.seriesId = seriesId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.releaseDate = releaseDate;
        this.magnetLink = magnetLink;
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

    public String getMagnetLink() {
        return magnetLink;
    }
}
