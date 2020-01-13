package bestworkingconditions.biedaflix.server.model;

import java.io.Serializable;

public class TorrentInfo implements Serializable {
    private float progress;
    private float eta;
    private String hash;
    private String name;

    public TorrentInfo() {
    }

    public TorrentInfo(float progress, float eta, String hash, String name) {
        this.progress = progress;
        this.eta = eta;
        this.hash = hash;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "[n:" + name +",p: " + progress + ", e:" + eta + "]";
    }
}
