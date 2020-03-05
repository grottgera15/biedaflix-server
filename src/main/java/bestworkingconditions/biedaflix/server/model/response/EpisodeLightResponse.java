package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.Episode;

import java.io.Serializable;
import java.util.Date;

public class EpisodeLightResponse implements Serializable {

    private String id;
    private int episodeNumber;

    private String name;
    private boolean available;
    private Date releaseDate;

    public EpisodeLightResponse() {
    }

    public EpisodeLightResponse(String id, int episodeNumber, String name, boolean available, Date releaseDate) {
        this.id = id;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.available = available;
        this.releaseDate = releaseDate;
    }

    public EpisodeLightResponse(Episode episode){
        this.id = episode.getId();
        this.episodeNumber = episode.getEpisodeNumber();
        this.name = episode.getName();
        this.available = episode.isAvailable();
        this.releaseDate = episode.getReleaseDate();
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
}
