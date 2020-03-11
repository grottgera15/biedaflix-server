package bestworkingconditions.biedaflix.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "currentlyDownloading")
@Getter
@Setter
@NoArgsConstructor
public class CurrentlyDownloading implements Serializable {

    @Id
    private String id;

    private Episode target;
    private TorrentInfo torrentInfo;

    @JsonProperty(value = "files")
    private List<TorrentFileInfo> torrentFileInfoList;

    public List<TorrentFileInfo> getTorrentFileInfoList() {
        return torrentFileInfoList;
    }

    public void setTorrentFileInfoList(List<TorrentFileInfo> torrentFileInfoList) {
        this.torrentFileInfoList = torrentFileInfoList;
    }
}
