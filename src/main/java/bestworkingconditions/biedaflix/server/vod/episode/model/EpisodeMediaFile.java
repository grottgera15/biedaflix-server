package bestworkingconditions.biedaflix.server.vod.episode.model;

import bestworkingconditions.biedaflix.server.vod.series.model.SeriesMediaFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class EpisodeMediaFile extends SeriesMediaFile {

    private String episodeId;

    public EpisodeMediaFile(String extension, @NotNull String seriesId, String episodeId) {
        super(extension, seriesId);
        this.episodeId = episodeId;
    }

    @Override
    public String getFilePath() {
        return super.getFilePath() + episodeId + "/";
    }
}
