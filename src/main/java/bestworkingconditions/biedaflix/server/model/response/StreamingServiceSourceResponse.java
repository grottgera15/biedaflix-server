package bestworkingconditions.biedaflix.server.model.response;

import java.io.Serializable;

public class StreamingServiceSourceResponse implements Serializable {
    private String id;
    private String name;

    public StreamingServiceSourceResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
