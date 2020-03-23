package bestworkingconditions.biedaflix.server.vod.streamingServiceSource.model;

import bestworkingconditions.biedaflix.server.file.FileResource;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "streamingServices")
@Data
public class StreamingServiceSource {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;

    private FileResource logo;
}
