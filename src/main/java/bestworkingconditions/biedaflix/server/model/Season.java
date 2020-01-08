package bestworkingconditions.biedaflix.server.model;

import java.util.ArrayList;
import java.util.List;

public class Season {
    private int seasonNumber;
    private List<Episode> episodes;

    public Season() {
        episodes = new ArrayList<>();
    }

    public Season(int seasonNumber) {
        this.seasonNumber = seasonNumber;
        episodes = new ArrayList<>();
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
