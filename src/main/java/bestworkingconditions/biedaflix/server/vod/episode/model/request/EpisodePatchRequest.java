package bestworkingconditions.biedaflix.server.vod.episode.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
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

}
