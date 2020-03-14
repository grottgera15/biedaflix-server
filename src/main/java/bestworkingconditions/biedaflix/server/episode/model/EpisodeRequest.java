package bestworkingconditions.biedaflix.server.episode.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
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
    @JsonFormat(pattern = "s" ,shape = JsonFormat.Shape.NUMBER)
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
}
