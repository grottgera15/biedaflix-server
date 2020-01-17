package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;

public abstract class SeriesMediaFiles extends  FileResource {

    @NotNull
    protected String seriesName;

    public SeriesMediaFiles() {
    }

    public SeriesMediaFiles(String mimeType, @NotNull String seriesName) {
        super(mimeType);
        this.seriesName = seriesName;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    @Override
    public String getFilePath() {
        return "/series/" + seriesName + "/";
    }
}
