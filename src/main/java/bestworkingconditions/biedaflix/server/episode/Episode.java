package bestworkingconditions.biedaflix.server.episode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "episodes")
@Getter
@Setter
@NoArgsConstructor
public class Episode {

    @Id
    private String id;

    private String seriesId;

    private int seasonNumber;
    private int episodeNumber;

    private String name;
    private EpisodeStatus episodeStatus;
    private Date releaseDate;

    private List<EpisodeVideo> videoFiles = new ArrayList<>();
    private List<EpisodeSubtitles> episodeSubtitles = new ArrayList<>();
    private List<EpisodeThumbs> episodeThumbs = new ArrayList<>();

    public Episode(String seriesId, int seasonNumber, int episodeNumber, String name, Date releaseDate) {
        this.seriesId = seriesId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.releaseDate = releaseDate;
        this.episodeStatus = EpisodeStatus.UNAVAILABLE;
    }

    public Episode(String id, String seriesId, int seasonNumber, int episodeNumber, String name, EpisodeStatus episodeStatus, Date releaseDate, List<EpisodeVideo> videoFiles, List<EpisodeSubtitles> episodeSubtitles, List<EpisodeThumbs> episodeThumbs) {
        this.id = id;
        this.seriesId = seriesId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.episodeStatus = episodeStatus;
        this.releaseDate = releaseDate;
        this.videoFiles = videoFiles;
        this.episodeSubtitles = episodeSubtitles;
        this.episodeThumbs = episodeThumbs;
    }
}
