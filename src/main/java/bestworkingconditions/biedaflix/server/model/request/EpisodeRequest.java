package bestworkingconditions.biedaflix.server.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

public class EpisodeRequest implements Serializable {

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

    private Optional<String> magnetLink;

    public EpisodeRequest() {
        magnetLink = Optional.empty();
    }

    public EpisodeRequest(@NotNull @NotBlank(message = "seriesId must not be blank!") String seriesId, @NotNull int seasonNumber, @NotNull int episodeNumber, @NotNull @NotBlank(message = "name must not be blank!") String name, @NotNull Date releaseDate, String magnetLink) {
        this.seriesId = seriesId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.releaseDate = releaseDate;
        this.magnetLink = Optional.of(magnetLink);
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Optional<String> getMagnetLink() {
        return magnetLink;
    }

    public void setMagnetLink(String magnetLink) {
        this.magnetLink = Optional.of(magnetLink);
    }
}
