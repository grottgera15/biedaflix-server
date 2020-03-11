package bestworkingconditions.biedaflix.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.MimeType;

import java.net.URI;
@Getter
@Setter
@NoArgsConstructor
public abstract class FileResource {

    @ContentId
    protected String contentId;
    protected String fileExtension;

    public abstract String getFilePath();

    public FileResource(String extension) {
        this.fileExtension = extension;
    }
}
