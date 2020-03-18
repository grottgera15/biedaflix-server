package bestworkingconditions.biedaflix.server.vod.streamingServiceSource;

import bestworkingconditions.biedaflix.server.file.FileResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;

@Document(collection = "streamingServices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StreamingServiceSource {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;

    @DBRef
    private FileResource logo;
}
