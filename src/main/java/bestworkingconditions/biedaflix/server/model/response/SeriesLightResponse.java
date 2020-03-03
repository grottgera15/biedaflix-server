package bestworkingconditions.biedaflix.server.model.response;

public class SeriesLightResponse {

    private String id;

    private String name;
    private String description;

    private MediaFilesResponse banner;
    private MediaFilesResponse logo;

    private String sourceId;
    private Boolean onGoing;

    public SeriesLightResponse() {
    }

    public SeriesLightResponse(String id, String name, String description, MediaFilesResponse banner, MediaFilesResponse logo, String sourceId, Boolean onGoing) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.banner = banner;
        this.logo = logo;
        this.sourceId = sourceId;
        this.onGoing = onGoing;
    }
}
