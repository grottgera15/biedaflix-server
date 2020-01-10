package bestworkingconditions.biedaflix.server.model;

import java.io.Serializable;

public class TorrentInfo implements Serializable {
    private float progress;
    private float eta;
    private String hash;

    public TorrentInfo() {
    }

    public TorrentInfo(float progress, float eta, String hash) {
        this.progress = progress;
        this.eta = eta;
        this.hash = hash;
    }

    public float getProgress() {
        return progress;
    }

    public float getEta() {
        return eta;
    }

    public String getHash() {
        return hash;
    }
}
