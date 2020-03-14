package bestworkingconditions.biedaflix.server.vod.streamingServiceSource;

import bestworkingconditions.biedaflix.server.common.model.FileResource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "streamingServices")
@Getter
@Setter
@NoArgsConstructor
public class StreamingServiceSource extends FileResource {

    @Id
    private String id;
    private String name;

    public StreamingServiceSource(String extension, String name) {
        super(extension);
        this.name = name;
    }

    @Override
    public String getFilePath() {
        return  "/" + getClass().getSimpleName() + "/" + id + "." + getFileExtension() ;
    }
}
