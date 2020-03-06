package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;

public class EpisodeThumbs extends EpisodeMediaFile {

    private String thumbName;

    public EpisodeThumbs() {
    }

    public EpisodeThumbs(String extension, @NotNull String seriesId, String episodeId, String thumbName) {
        super(extension, seriesId, episodeId);
        this.thumbName = thumbName;
    }

    public String getThumbName() {
        return thumbName;
    }

    public void setThumbName(String thumbName) {
        this.thumbName = thumbName;
    }

    @Override
    public String getFilePath() {
        return super.getFilePath() + "thumbs/" + thumbName +"."+ fileExtension;
    }
}
