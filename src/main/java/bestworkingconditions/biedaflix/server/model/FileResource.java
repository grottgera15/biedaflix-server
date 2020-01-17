package bestworkingconditions.biedaflix.server.model;

import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.MimeType;

import java.net.URI;

public abstract class FileResource {

    @ContentId
    protected String contentId;
    @MimeType
    protected String mimeType;


    public abstract URI getResourceURI();
    public abstract String getFilePath();

    public FileResource() {
    }

    public FileResource(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
