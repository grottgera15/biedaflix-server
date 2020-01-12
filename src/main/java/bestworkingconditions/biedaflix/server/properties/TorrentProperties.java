package bestworkingconditions.biedaflix.server.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "torrent")
public class TorrentProperties {
    private String clientUri;
    private String clientPort;
    private String pathToResources;

    public String getClientUri() {
        return clientUri;
    }

    public void setClientUri(String clientUri) {
        this.clientUri = clientUri;
    }

    public String getClientPort() {
        return clientPort;
    }

    public void setClientPort(String clientPort) {
        this.clientPort = clientPort;
    }

    public String getPathToResources() {
        return pathToResources;
    }

    public void setPathToResources(String pathToResources) {
        this.pathToResources = pathToResources;
    }
}
