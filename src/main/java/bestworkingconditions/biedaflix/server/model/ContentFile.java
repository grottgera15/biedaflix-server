package bestworkingconditions.biedaflix.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.content.commons.annotations.MimeType;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ContentFile {

    @Id @ContentId
    private String contentID;
    @ContentLength private long contentLength;

    @MimeType private String mimeType;

    public ContentFile(String contentID, long contentLength, String mimeType) {
        this.contentID = contentID;
        this.contentLength = contentLength;
        this.mimeType = mimeType;
    }
}
