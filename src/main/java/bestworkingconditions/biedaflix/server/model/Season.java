package bestworkingconditions.biedaflix.server.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "seasons")
public class Season {
    private int seasonNumber;

}
