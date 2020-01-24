package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.EpisodeThumbs;

import java.io.Serializable;
import java.util.*;

public class EpisodeResponse implements Serializable {

    private String id;
    private int episodeNumber;

    private String name;
    private boolean available;
    private Date releaseDate;

    private Map<String, String> videoSources = new HashMap<>();
    private Map<String,String> subtitles = new HashMap<>();
    private List<EpisodeThumbs> thubs = new ArrayList<>();

    public EpisodeResponse() {
    }

    public EpisodeResponse(String id, int episodeNumber, String name, boolean available, Date releaseDate, Map<String, String> videoSources, Map<String, String> subtitles, List<EpisodeThumbs> thubs) {
        this.id = id;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.available = available;
        this.releaseDate = releaseDate;
        this.videoSources = videoSources;
        this.subtitles = subtitles;
        this.thubs = thubs;
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

    public List<EpisodeThumbs> getThubs() {
        return thubs;
    }

    public void setThubs(List<EpisodeThumbs> thubs) {
        this.thubs = thubs;
    }
}
