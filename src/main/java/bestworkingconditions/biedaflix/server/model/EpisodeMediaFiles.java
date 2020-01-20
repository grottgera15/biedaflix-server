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

    @Override
    public String getSeriesName() {
        return super.getSeriesName() + "S" + season + "/E" + episode + "/";
    }
}
