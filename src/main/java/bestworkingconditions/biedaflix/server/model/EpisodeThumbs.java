package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;

public class EpisodeThumbs extends EpisodeMediaFiles {

    private String thumbName;

    public EpisodeThumbs() {
    }

    public EpisodeThumbs(String extension, @NotNull String seriesName, int season, int episode, String thumbName) {
        super(extension, seriesName, season, episode);
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
