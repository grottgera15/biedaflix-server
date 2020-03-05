package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;

public class SeriesBanner extends SeriesMediaFile {

    public SeriesBanner() {
    }

    public SeriesBanner(String extension, @NotNull String seriesName) {
        super(extension, seriesName);
    }

    @Override
    public String getFilePath() {
        return super.getFilePath() + "banner" + "." + getFileExtension();
    }
}
