package bestworkingconditions.biedaflix.server.vod.series.model;

import javax.validation.constraints.NotNull;

public class SeriesBanner extends SeriesMediaFile {

    public SeriesBanner() {
    }

    public SeriesBanner(String extension, @NotNull String seriesName) {
        super(extension, seriesName);
    }

    @Override
    public String getFilePath() {
        if(this.fileExtension.isEmpty()){

        }
        return super.getFilePath() + "banner" + "." + getFileExtension();
    }
}
