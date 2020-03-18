package bestworkingconditions.biedaflix.server.vod.streamingServiceSource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "streamingServices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StreamingServiceSource {

    @Id
    private String id;
    private String name;
}
