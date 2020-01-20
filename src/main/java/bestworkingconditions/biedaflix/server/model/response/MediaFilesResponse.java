package bestworkingconditions.biedaflix.server.model.response;

import java.net.URI;
import java.net.URL;

public class MediaFilesResponse {

    private URL path;

    public MediaFilesResponse(URL path) {
        this.path = path;
    }

    public URL getPath() {
        return path;
    }

    public void setPath(URL path) {
        this.path = path;
    }
}
