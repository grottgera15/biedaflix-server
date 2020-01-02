package bestworkingconditions.biedaflix.server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "episodes")
public class Episode {

    @org.springframework.data.annotation.Id
    private String Id;

    private int number;
    private String name;
    private boolean available;
    private Date releaseDate;


}
