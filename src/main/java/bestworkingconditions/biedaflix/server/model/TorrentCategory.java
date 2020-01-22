package bestworkingconditions.biedaflix.server.model;

public enum TorrentCategory {

    BIEDAFLIX("biedaflix"),
    BIEDAFLIX_FINISHED("biedaflix_finished");

    private String categoryValue;

    TorrentCategory(String categoryValue){
        this.categoryValue = categoryValue;
    }

    public String getCategoryValue() {
        return categoryValue;
    }
}
