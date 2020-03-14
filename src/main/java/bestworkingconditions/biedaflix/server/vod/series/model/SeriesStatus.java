package bestworkingconditions.biedaflix.server.vod.series.model;

public enum SeriesStatus {

    UNAVAILABLE("unavailable"),
    ANNOUNCED("announced"),
    ONGOING("ongoing"),
    FINISHED("finished"),
    NEW_EPISODES("new_episodes")
    ;

    private String statusString;

    SeriesStatus(String statusString) {
        this.statusString = statusString;
    }

    public String getStatusString() {
        return statusString;
    }
}
