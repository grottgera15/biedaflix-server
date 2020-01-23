package bestworkingconditions.biedaflix.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TorrentInfo implements Serializable {
    private float progress;
    private float eta;
    private String hash;
    private String name;

    @JsonProperty("save_path")
    private String saveDirectory;

    public TorrentInfo() {
    }

    public TorrentInfo(float progress, float eta, String hash, String name, String saveDirectory) {
        this.progress = progress;
        this.eta = eta;
        this.hash = hash;
        this.name = name;
        this.saveDirectory = saveDirectory;
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

    public String getSaveDirectory() {
        return saveDirectory;
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
