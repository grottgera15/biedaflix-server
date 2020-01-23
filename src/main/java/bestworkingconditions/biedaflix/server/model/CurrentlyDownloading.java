package bestworkingconditions.biedaflix.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document(collection = "currentlyDownloading")
public class CurrentlyDownloading implements Serializable {

    @Id
    private String id;

    private Episode target;
    private TorrentInfo torrentInfo;

    @JsonProperty(value = "files")
    private List<TorrentFileInfo> torrentFileInfoList;

    public CurrentlyDownloading() {
    }

    public List<TorrentFileInfo> getTorrentFileInfoList() {
        return torrentFileInfoList;
    }

    public void setTorrentFileInfoList(List<TorrentFileInfo> torrentFileInfoList) {
        this.torrentFileInfoList = torrentFileInfoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Episode getTarget() {
        return target;
    }

    public void setTarget(Episode target) {
        this.target = target;
    }

    public TorrentInfo getTorrentInfo() {
        return torrentInfo;
    }

    public void setTorrentInfo(TorrentInfo torrentInfo) {
        this.torrentInfo = torrentInfo;
    }
}
