package bestworkingconditions.biedaflix.server.model;

public enum SeriesStatus {

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
