package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.SeriesStatus;

import java.io.Serializable;

public class SeriesLightResponse implements Serializable {

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MediaFilesResponse getBanner() {
        return banner;
    }

    public void setBanner(MediaFilesResponse banner) {
        this.banner = banner;
    }

    public MediaFilesResponse getLogo() {
        return logo;
    }

    public void setLogo(MediaFilesResponse logo) {
        this.logo = logo;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public SeriesStatus getStatus() {
        return status;
    }

    public void setStatus(SeriesStatus status) {
        this.status = status;
    }
}
