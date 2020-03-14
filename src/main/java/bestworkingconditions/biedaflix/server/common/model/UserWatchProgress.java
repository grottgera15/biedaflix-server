package bestworkingconditions.biedaflix.server.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "userWatchProgress")
@Getter @Setter @NoArgsConstructor
public class UserWatchProgress implements Serializable {

    @Id
    private String id;

    private String userId;
    private Map<String,Double> progress = new HashMap<>();

    public UserWatchProgress(String userId) {
        progress = new HashMap<>();
        this.userId = userId;
    }
}
