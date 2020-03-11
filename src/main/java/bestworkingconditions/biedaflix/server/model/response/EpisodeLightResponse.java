package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.EpisodeStatus;

import java.io.Serializable;
import java.util.Date;

public class EpisodeLightResponse implements Serializable {

    private String id;
    private int episodeNumber;
    private int episodeSeason;

    private String name;
    private EpisodeStatus status;
    private Date releaseDate;

    public EpisodeLightResponse() {
    }

    public EpisodeLightResponse(String id, int episodeNumber, int episodeSeason, String name, EpisodeStatus status, Date releaseDate) {
        this.id = id;
        this.episodeNumber = episodeNumber;
        this.episodeSeason = episodeSeason;
        this.name = name;
        this.status = status;
        this.releaseDate = releaseDate;
    }

    public EpisodeLightResponse(Episode episode){
        this.id = episode.getId();
        this.episodeNumber = episode.getEpisodeNumber();
        this.episodeSeason = episode.getSeasonNumber();
        this.name = episode.getName();
        this.status = episode.getEpisodeStatus();
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

    public int getEpisodeSeason() {
        return episodeSeason;
    }

    public void setEpisodeSeason(int episodeSeason) {
        this.episodeSeason = episodeSeason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EpisodeStatus getStatus() {
        return status;
    }

    public void setStatus(EpisodeStatus status) {
        this.status = status;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
