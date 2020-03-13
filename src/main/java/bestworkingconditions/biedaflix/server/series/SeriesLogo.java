package bestworkingconditions.biedaflix.server.series;

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
