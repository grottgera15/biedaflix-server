package bestworkingconditions.biedaflix.server.vod.episode.model.response;

import bestworkingconditions.biedaflix.server.vod.episode.model.EpisodeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeLightResponse implements Serializable {

    private String id;
    private int episodeNumber;
    private int seasonNumber;

    private String name;
    private EpisodeStatus status;
    private Date releaseDate;
}
