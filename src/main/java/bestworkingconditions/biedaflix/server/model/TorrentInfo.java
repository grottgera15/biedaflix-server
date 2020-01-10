package bestworkingconditions.biedaflix.server.model;

public class TorrentInfo {
    private float progress;
    private float eta;

    public TorrentInfo() {
    }

    public TorrentInfo(float progress, float eta) {
        this.progress = progress;
        this.eta = eta;
    }

    public float getProgress() {
        return progress;
    }

    public float getEta() {
        return eta;
    }
}
