package bestworkingconditions.biedaflix.server.vod.episode.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeRequest implements Serializable {

    @NotNull
    @NotBlank()
    private String seriesId;

    @NotNull
    private int seasonNumber;

    @NotNull
    private int episodeNumber;

    @NotNull
    @NotBlank()
    private String name;

    @JsonFormat(pattern = "s" ,shape = JsonFormat.Shape.NUMBER)
    private Date releaseDate;

    private String magnetLink;
}
