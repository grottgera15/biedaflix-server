package bestworkingconditions.biedaflix.server.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "userWatchProgress")
public class UserWatchProgress implements Serializable {

    @Id
    private String id;

    private String userId;
    private Map<String,Double> progress;

    public UserWatchProgress() {
        progress = new HashMap<>();
    }

    public UserWatchProgress(String userId) {
        progress = new HashMap<>();
        this.userId = userId;
    }

    public UserWatchProgress(String userId, Map<String, Double> progress) {
        this.userId = userId;
        this.progress = progress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Double> getProgress() {
        return progress;
    }

    public void setProgress(Map<String, Double> progress) {
        this.progress = progress;
    }
}
