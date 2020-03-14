package bestworkingconditions.biedaflix.server.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TorrentFileInfo implements Serializable {

    @JsonProperty("name")
    private String relativePath;

    @JsonProperty("size")
    private long fileSize;

    public TorrentFileInfo(String relativePath, long fileSize) {
        this.relativePath = relativePath;
        this.fileSize = fileSize;
    }
}
