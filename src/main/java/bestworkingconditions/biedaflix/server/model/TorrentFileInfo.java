package bestworkingconditions.biedaflix.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class TorrentFileInfo implements Serializable {

    @JsonProperty("name")
    private String relativePath;

    @JsonProperty("size")
    private long fileSize;

    public TorrentFileInfo() {
    }

    public TorrentFileInfo(String relativePath, long fileSize) {
        this.relativePath = relativePath;
        this.fileSize = fileSize;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
