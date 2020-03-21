package bestworkingconditions.biedaflix.server.vod.streamingServiceSource;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StreamingServiceSourceRequest {

    @NotBlank
    private String name;
}
