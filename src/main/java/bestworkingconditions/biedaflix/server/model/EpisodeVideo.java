package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;

public class EpisodeVideo extends EpisodeMediaFile {

    public enum VideoQuality{
        HIGH("1080"),
        MEDIUM("720"),
        LOW("480");

        private String quality;

        VideoQuality(String quality) {
            this.quality = quality;
        }

        public String getQuality() {
            return quality;
        }
    }

    private VideoQuality videoQuality;

    public EpisodeVideo() {
    }

    public EpisodeVideo(String extension, @NotNull String seriesId, String episodeId, VideoQuality videoQuality) {
        super(extension, seriesId, episodeId);
        this.videoQuality = videoQuality;
    }

    public VideoQuality getVideoQuality() {
        return videoQuality;
    }

    public void setVideoQuality(VideoQuality videoQuality) {
        this.videoQuality = videoQuality;
    }

    @Override
    public String getFilePath() {
        return super.getFilePath() + videoQuality.getQuality() + "." + fileExtension;
    }
}


