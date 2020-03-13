package bestworkingconditions.biedaflix.server.model.response;


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
