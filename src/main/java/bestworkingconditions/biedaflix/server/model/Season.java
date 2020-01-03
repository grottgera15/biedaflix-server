package bestworkingconditions.biedaflix.server.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public class Season {
    private int seasonNumber;
    @DBRef
    private List<Episode> episodes;
}
