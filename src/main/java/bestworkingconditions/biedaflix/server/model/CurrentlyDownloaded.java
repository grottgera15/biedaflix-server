package bestworkingconditions.biedaflix.server.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "currentlyDownloaded")
public class CurrentlyDownloaded {

    private Episode target;
    private String torrentHash;

    public CurrentlyDownloaded() {
    }

    public CurrentlyDownloaded(Episode target, String torrentHash) {
        this.target = target;
        this.torrentHash = torrentHash;
    }

    public Episode getTarget() {
        return target;
    }

    public void setTarget(Episode target) {
        this.target = target;
    }

    public String getTorrentHash() {
        return torrentHash;
    }

    public void setTorrentHash(String torrentHash) {
        this.torrentHash = torrentHash;
    }
}
