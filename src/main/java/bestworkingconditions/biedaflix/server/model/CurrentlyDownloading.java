package bestworkingconditions.biedaflix.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "currentlyDownloading")
public class CurrentlyDownloading {

    @Id
    private String id;

    private Episode target;
    private TorrentInfo torrentInfo;

    public CurrentlyDownloading() {
    }

    public CurrentlyDownloading(String id, Episode target, TorrentInfo torrentInfo) {
        this.id = id;
        this.target = target;
        this.torrentInfo = torrentInfo;
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
