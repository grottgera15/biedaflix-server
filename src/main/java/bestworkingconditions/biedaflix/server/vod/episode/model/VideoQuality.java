package bestworkingconditions.biedaflix.server.vod.episode.model;

public enum VideoQuality {
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
