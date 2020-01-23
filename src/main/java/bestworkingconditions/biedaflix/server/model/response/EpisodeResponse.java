package bestworkingconditions.biedaflix.server.model.response;

import javax.print.DocFlavor;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EpisodeResponse {

    private String id;
    private int episodeNumber;

    private String name;
    private boolean available;
    private Date releaseDate;

    private Map<String, String> videoSources = new HashMap<>();
    private Map<String,String> subtitles = new HashMap<>();

    public EpisodeResponse() {
    }

    public EpisodeResponse(String id, int episodeNumber, String name, boolean available, Date releaseDate, Map<String, String> videoSources, Map<String, String> subtitles) {
        this.id = id;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.available = available;
        this.releaseDate = releaseDate;
        this.videoSources = videoSources;
        this.subtitles = subtitles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Map<String, String> getVideoSources() {
        return videoSources;
    }

    public void setVideoSources(Map<String, String> videoSources) {
        this.videoSources = videoSources;
    }

    public Map<String, String> getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(Map<String, String> subtitles) {
        this.subtitles = subtitles;
    }
}
