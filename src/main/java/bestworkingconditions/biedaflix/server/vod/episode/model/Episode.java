package bestworkingconditions.biedaflix.server.vod.episode.model;
import bestworkingconditions.biedaflix.server.file.FileResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@CompoundIndex(name = "series_season_episode",def = "{'seriesId' : 1,'seasonNumber':1,'episodeNumber':1}",unique = true)
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
    private EpisodeStatus status;
    private Date releaseDate;
    private Double size;

    @DBRef
    private Map<VideoQuality,FileResource> videos = new HashMap<>();
    @DBRef
    private Map<String,FileResource> subtitles = new HashMap<>();
    @DBRef
    private List<FileResource> thumbs = new ArrayList<>();
}
