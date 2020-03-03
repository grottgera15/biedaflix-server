package bestworkingconditions.biedaflix.server.model.response;

import java.io.Serializable;
import java.net.URL;

public class StreamingServiceSourceResponse implements  Serializable{
    private String id;
    private String name;
    private URL path;

    public StreamingServiceSourceResponse(String id, String name,URL path) {
        this.id = id;
        this.name = name;
        this.path = path;
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

    public URL getPath() {
        return path;
    }

    public void setPath(URL path) {
        this.path = path;
    }
}
