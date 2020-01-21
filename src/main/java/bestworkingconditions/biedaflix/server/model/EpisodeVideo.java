package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;

public class EpisodeVideo extends  EpisodeMediaFiles {

    private Episode.VideoQuality videoQuality;


    public EpisodeVideo() {
    }

    public EpisodeVideo(String extension, @NotNull String seriesName, int season, int episode, Episode.VideoQuality videoQuality) {
        super(extension, seriesName, season, episode);
        this.videoQuality = videoQuality;
    }

    public Episode.VideoQuality getVideoQuality() {
        return videoQuality;
    }

    public void setVideoQuality(Episode.VideoQuality videoQuality) {
        this.videoQuality = videoQuality;
    }

    @Override
    public String getFilePath() {
        return super.getFilePath() + seriesName;
    }
}


