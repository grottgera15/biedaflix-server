package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;
import java.net.URI;

public class SeriesLogo extends SeriesMediaFiles {

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
