package bestworkingconditions.biedaflix.server.model;

import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.MimeType;

import java.net.URI;

public abstract class FileResource {

    @ContentId
    protected String contentId;
    protected String fileExtension;

    public abstract URI getResourceURI();
    public abstract String getFilePath();

    public FileResource() {
    }

    public FileResource(String extension) {
        this.fileExtension = extension;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
