package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;
import java.net.URI;

public class SeriesLogo extends SeriesMediaFiles {

    public SeriesLogo(String mimeType, @NotNull String seriesName) {
        super(mimeType, seriesName);
    }

    @Override
    public URI getResourceURI() {
        return null;
    }

    @Override
    public String getFilePath() {
        return super.getFilePath() + "logo";
    }

}
