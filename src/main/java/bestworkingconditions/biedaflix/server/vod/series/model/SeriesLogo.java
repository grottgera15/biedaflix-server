package bestworkingconditions.biedaflix.server.vod.series.model;

import javax.validation.constraints.NotNull;

public class SeriesLogo extends SeriesMediaFile {

    public SeriesLogo() {
    }

    public SeriesLogo(String extension, @NotNull String seriesName) {
        super(extension, seriesName);
    }

    @Override
    public String getFilePath() {
        return super.getFilePath() + "logo" + "." + getFileExtension();
    }
}
