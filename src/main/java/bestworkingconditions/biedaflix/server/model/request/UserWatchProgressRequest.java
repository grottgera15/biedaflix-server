package bestworkingconditions.biedaflix.server.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserWatchProgressRequest implements Serializable {

    @NotBlank
    private String episodeId;
    @NotNull
    private Double time;

    public UserWatchProgressRequest() {
    }

    public UserWatchProgressRequest(String episodeId, Double time) {
        this.episodeId = episodeId;
        this.time = time;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(String episodeId) {
        this.episodeId = episodeId;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
