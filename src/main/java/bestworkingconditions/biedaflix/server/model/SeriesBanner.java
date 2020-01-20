package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;
import java.net.URI;

public class SeriesBanner extends SeriesMediaFiles {

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
