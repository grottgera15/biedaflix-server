package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;

public class EpisodeMediaFiles extends SeriesMediaFiles {

    private int season;
    private int episode;

    public EpisodeMediaFiles() {
    }

    public EpisodeMediaFiles(String extension, @NotNull String seriesName, int season, int episode) {
        super(extension, seriesName);
        this.season = season;
        this.episode = episode;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    @Override
    public String getFilePath() {
        return super.getFilePath() + "S" + season + "/E" + episode + "/";
    }
}
