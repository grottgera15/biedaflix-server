package bestworkingconditions.biedaflix.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "series")
public class Series {

    @Id
    private String id;

    private String name;
    private String description;

    private SeriesBanner seriesBanner;
    private SeriesLogo logo;

    private String streamingServiceId;
    private SeriesStatus status;

    public Series() {
    }

    public Series(String id, String name, String description, SeriesBanner seriesBanner, SeriesLogo logo, String streamingServiceId, SeriesStatus status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.seriesBanner = seriesBanner;
        this.logo = logo;
        this.streamingServiceId = streamingServiceId;
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

    public SeriesBanner getSeriesBanner() {
        return seriesBanner;
    }

    public void setSeriesBanner(SeriesBanner seriesBanner) {
        this.seriesBanner = seriesBanner;
    }

    public SeriesLogo getLogo() {
        return logo;
    }

    public void setLogo(SeriesLogo logo) {
        this.logo = logo;
    }

    public String getStreamingServiceId() {
        return streamingServiceId;
    }

    public void setStreamingServiceId(String streamingServiceId) {
        this.streamingServiceId = streamingServiceId;
    }

    public SeriesStatus getStatus() {
        return status;
    }

    public void setStatus(SeriesStatus status) {
        this.status = status;
    }

    public String getFolderName(){
        return this.name.replace(' ','_');
    }
}
