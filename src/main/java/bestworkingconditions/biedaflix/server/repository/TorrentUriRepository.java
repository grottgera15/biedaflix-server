package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.properties.TorrentProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TorrentUriRepository {

    private final TorrentProperties properties;

    @Autowired
    public TorrentUriRepository(TorrentProperties properties) {this.properties = properties;}

    public String getUri(String function){
        return "http://" + properties.getClientUri() + ":" + properties.getClientPort() + properties.getPathToResources() + function;
    }
}
