package bestworkingconditions.biedaflix.server.vod.streamingServiceSource.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.net.URL;

@Data
@NoArgsConstructor
public class StreamingServiceSourceResponse implements  Serializable{
    private String id;
    private String name;
    private URL path;
}
