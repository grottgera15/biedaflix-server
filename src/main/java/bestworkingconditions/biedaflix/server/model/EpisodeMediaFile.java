package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;

public class EpisodeMediaFile extends SeriesMediaFile {

    private String episodeId;

    public EpisodeMediaFile() {
    }

    public EpisodeMediaFile(String extension, @NotNull String seriesId, String episodeId) {
        super(extension, seriesId);
        this.episodeId = episodeId;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    @Override
    public String getFilePath() {
        return super.getFilePath() + episodeId + "/";
    }
}
