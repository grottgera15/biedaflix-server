package bestworkingconditions.biedaflix.server.model.response;

import java.io.Serializable;
import java.net.URI;
import java.net.URL;

public class StreamingServiceSourceResponse implements  Serializable{
    private String id;
    private String name;
    private URL resourcePath;

    public StreamingServiceSourceResponse(String id, String name,URL resourcePath) {
        this.id = id;
        this.name = name;
        this.resourcePath = resourcePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(URL resourcePath) {
        this.resourcePath = resourcePath;
    }
}
