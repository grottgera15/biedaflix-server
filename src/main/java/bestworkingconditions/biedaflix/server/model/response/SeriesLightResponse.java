package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.SeriesStatus;

public class SeriesLightResponse {

    private String id;

    private String name;
    private String description;

    private MediaFilesResponse banner;
    private MediaFilesResponse logo;

    private String sourceId;

    private SeriesStatus status;

    public SeriesLightResponse() {
    }

    public SeriesLightResponse(String id, String name, String description, MediaFilesResponse banner, MediaFilesResponse logo, String sourceId, SeriesStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.banner = banner;
        this.logo = logo;
        this.sourceId = sourceId;
        this.status = status;
    }
}
