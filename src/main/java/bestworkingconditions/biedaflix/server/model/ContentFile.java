package bestworkingconditions.biedaflix.server.model;

import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;

import javax.persistence.Entity;

@Entity
public class SeriesLogo {

    @ContentId private String contentID;
    @ContentLength private long contentLength;
    private String mimeType;

    public SeriesLogo() {
    }

    public SeriesLogo(String contentID, long contentLength, String mimeType) {
        this.contentID = contentID;
        this.contentLength = contentLength;
        this.mimeType = mimeType;
    }

    public String getContentID() {
        return contentID;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
