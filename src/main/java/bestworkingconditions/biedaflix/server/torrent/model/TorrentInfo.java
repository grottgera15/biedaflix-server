package bestworkingconditions.biedaflix.server.torrent.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TorrentInfo implements Serializable {
    private float progress;
    private float eta;
    private String hash;
    private String name;

    @JsonProperty("save_path")
    private String saveDirectory;

    public TorrentInfo(float progress, float eta, String hash, String name, String saveDirectory) {
        this.progress = progress;
        this.eta = eta;
        this.hash = hash;
        this.name = name;
        this.saveDirectory = saveDirectory;
    }

    @Override
    public String toString() {
        return "[n:" + name +",p: " + progress + ", e:" + eta + "]";
    }
}
