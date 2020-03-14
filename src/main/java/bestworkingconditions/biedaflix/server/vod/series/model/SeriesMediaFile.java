package bestworkingconditions.biedaflix.server.vod.series.model;

import bestworkingconditions.biedaflix.server.common.model.FileResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public abstract class SeriesMediaFile extends FileResource {

    @NotNull
    protected String seriesId;

    public SeriesMediaFile(String extension, @NotNull String seriesId) {
        super(extension);
        this.seriesId = seriesId;
    }

    @Override
    public String getFilePath() {
        return "series/" + seriesId + "/";
    }
}
