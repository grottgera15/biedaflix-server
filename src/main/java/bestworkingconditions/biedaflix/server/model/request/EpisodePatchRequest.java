package bestworkingconditions.biedaflix.server.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.swing.text.html.Option;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

public class EpisodePatchRequest implements Serializable {

    private Optional<String> seriesId;
    private Optional<Integer> seasonNumber;
    private Optional<Integer> episodeNumber;
    private Optional<String> name;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Optional<Date> releaseDate;
    private Optional<String> magnetLink;

    public EpisodePatchRequest() {
        seriesId = Optional.empty();
        seasonNumber = Optional.empty();
        episodeNumber = Optional.empty();
        name = Optional.empty();
        releaseDate = Optional.empty();
        magnetLink = Optional.empty();

    }

    public EpisodePatchRequest(Optional<String> seriesId, Optional<Integer> seasonNumber, Optional<Integer> episodeNumber, Optional<String> name, Optional<Date> releaseDate, Optional<String> magnetLink) {
        this.seriesId = seriesId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.releaseDate = releaseDate;
        this.magnetLink = magnetLink;
    }

    public Optional<String> getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Optional<String> seriesId) {
        this.seriesId = seriesId;
    }

    public Optional<Integer> getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Optional<Integer> seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Optional<Integer> getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Optional<Integer> episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Optional<String> getName() {
        return name;
    }

    public void setName(Optional<String> name) {
        this.name = name;
    }

    public Optional<Date> getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Optional<Date> releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Optional<String> getMagnetLink() {
        return magnetLink;
    }

    public void setMagnetLink(Optional<String> magnetLink) {
        this.magnetLink = magnetLink;
    }
}
