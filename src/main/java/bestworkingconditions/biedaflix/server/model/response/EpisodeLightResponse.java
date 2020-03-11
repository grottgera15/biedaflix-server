package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.EpisodeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class EpisodeLightResponse implements Serializable {

    private String id;
    private int episodeNumber;
    private int seasonNumber;

    private String name;
    private EpisodeStatus status;
    private Date releaseDate;

    public EpisodeLightResponse(String id, int episodeNumber, int episodeSeason, String name, EpisodeStatus status, Date releaseDate) {
        this.id = id;
        this.episodeNumber = episodeNumber;
        this.episodeNumber = episodeSeason;
        this.name = name;
        this.status = status;
        this.releaseDate = releaseDate;
    }

    public EpisodeLightResponse(Episode episode){
        this.id = episode.getId();
        this.episodeNumber = episode.getEpisodeNumber();
        this.episodeNumber = episode.getSeasonNumber();
        this.name = episode.getName();
        this.status = episode.getEpisodeStatus();
        this.releaseDate = episode.getReleaseDate();
    }
}
