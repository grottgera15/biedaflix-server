package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;
import java.net.URI;

public class SeriesBanner extends SeriesMediaFiles {

    public SeriesBanner(String mimeType, @NotNull String seriesName) {
        super(mimeType, seriesName);
    }

    @Override
    public URI getResourceURI() {
        return null;
    }

    @Override
    public String getFilePath() {
        return super.getFilePath() + "banner";
    }
}
