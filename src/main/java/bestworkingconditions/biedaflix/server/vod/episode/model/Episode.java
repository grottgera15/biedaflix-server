package bestworkingconditions.biedaflix.server.vod.episode.model;
import bestworkingconditions.biedaflix.server.file.FileResource;
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
    private Double size;

    private List<EpisodeVideo> videoFiles = new ArrayList<>();
    private List<EpisodeSubtitles> episodeSubtitles = new ArrayList<>();
    private List<FileResource> episodeThumbs = new ArrayList<>();
}
