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
    private String id;

    private String seriesId;

    private int seasonNumber;
    private int episodeNumber;

    private String name;
    private boolean available;
    private Date releaseDate;

    private Map<VideoQuality,String> videoQualities;
    private EpisodeSubtitles episodeSubtitles;


    public Episode() {
    }

    public Episode(String seriesId, int seasonNumber, int episodeNumber, String name, Date releaseDate) {
        this.seriesId = seriesId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Map<VideoQuality, String> getVideoQualities() {
        return videoQualities;
    }

    public void setVideoQualities(Map<VideoQuality, String> videoQualities) {
        this.videoQualities = videoQualities;
    }

    public EpisodeSubtitles getEpisodeSubtitles() {
        return episodeSubtitles;
    }

    public void setEpisodeSubtitles(EpisodeSubtitles episodeSubtitles) {
        this.episodeSubtitles = episodeSubtitles;
    }

}
