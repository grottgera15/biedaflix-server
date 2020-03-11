package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.SeriesMediaFile;

import java.net.URL;


public class MediaFilesResponse {

    private String path;

    public MediaFilesResponse(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
