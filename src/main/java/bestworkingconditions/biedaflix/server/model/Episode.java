package bestworkingconditions.biedaflix.server.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Document(collection = "episodes")
public class Episode {

    public enum VideoQuality {
        LOW,
        MID,
        HIGH;
    }

    public enum SubtitlesLanguage{
        PL,
        ENG;
    }

    @Id
    private String Id;

    private int episodeNumber;
    private String name;
    private boolean available;
    private Date releaseDate;

    private Map<VideoQuality,String> videoQualities;
    private Map<SubtitlesLanguage,String> subtitles;

    public Episode() {
    }

    public Episode(int episodeNumber, String name, Date releaseDate) {
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.releaseDate = releaseDate;
        this.available = false;
    }

    public Episode(String id, int episodeNumber, String name, boolean available, Date releaseDate, Map<VideoQuality, String> videoQualities, Map<SubtitlesLanguage, String> subtitles) {
        Id = id;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.available = available;
        this.releaseDate = releaseDate;
        this.videoQualities = videoQualities;
        this.subtitles = subtitles;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }
}
