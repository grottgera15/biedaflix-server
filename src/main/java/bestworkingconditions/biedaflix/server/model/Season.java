package bestworkingconditions.biedaflix.server.model;

import java.util.List;

public class Season {
    private int seasonNumber;
    private List<Episode> episodes;

    public Season() {
    }

    public Season(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }
}
