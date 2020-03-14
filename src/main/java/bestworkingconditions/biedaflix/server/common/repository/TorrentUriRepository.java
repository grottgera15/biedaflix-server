package bestworkingconditions.biedaflix.server.common.repository;

import bestworkingconditions.biedaflix.server.common.properties.TorrentProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TorrentUriRepository {

    private final TorrentProperties properties;

    @Autowired
    public TorrentUriRepository(TorrentProperties properties) {this.properties = properties;}

    public String getTorrentUri(String function){

        String clientPort = properties.getClientPort();

        if(clientPort.isEmpty()){
            return "http://" + properties.getClientUri() + properties.getPathToResources() + function;
        }
        else {
            return "http://" + properties.getClientUri() + ":" + properties.getClientPort() + properties.getPathToResources() + function;
        }
    }

    public String getAuthUri(String function){

        String clientPort = properties.getClientPort();

        if(clientPort.isEmpty()) {
            return "http://" + properties.getClientUri() + "/api/v2/auth/" + function;
        }
        else{
            return "http://" + properties.getClientUri() + ":" + properties.getClientPort() + "/api/v2/auth/" + function;
        }
    }
}
