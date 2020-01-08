package bestworkingconditions.biedaflix.server.model.request;

import java.util.Date;

public class EpisodeRequest {

    private String seriesId;
    private int seasonNumber;
    private int episodeNumber;
    private String name;

    private Date releaseDate;

    public EpisodeRequest() {
    }

    public EpisodeRequest(String seriesId, int seasonNumber, int episodeNumber, String name, Date releaseDate) {
        this.seriesId = seriesId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public String getName() {
        return name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }
}
