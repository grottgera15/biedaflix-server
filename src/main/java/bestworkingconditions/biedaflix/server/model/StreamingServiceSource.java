package bestworkingconditions.biedaflix.server.model;

import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.MimeType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "streamingServices")
public class StreamingServiceSource {

    @Id
    private String id;
    private String name;

    private @ContentId String contentID;
    @MimeType private String mimeType;

    public StreamingServiceSource() {
    }

    public StreamingServiceSource(String id, String name, String contentID, String mimeType) {
        this.id = id;
        this.name = name;
        this.contentID = contentID;
        this.mimeType = mimeType;
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

    public String getContentID() {
        return contentID;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
