package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;

public abstract class SeriesMediaFile extends  FileResource {

    @NotNull
    protected String seriesId;

    public SeriesMediaFile() {
    }

    public SeriesMediaFile(String extension, @NotNull String seriesId) {
        super(extension);
        this.seriesId = seriesId;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    @Override
    public String getFilePath() {
        return "series/" + seriesId + "/";
    }
}
