package bestworkingconditions.biedaflix.server.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "series")
public class Series {

    @Id
    private ObjectId id;

    private String name;
    private String description;
    private String bannerVideo;
    private String logo;
    private String source;
    private String onGoing;


}
