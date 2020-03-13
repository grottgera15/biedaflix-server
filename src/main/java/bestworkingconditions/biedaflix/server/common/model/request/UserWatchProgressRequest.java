package bestworkingconditions.biedaflix.server.common.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserWatchProgressRequest implements Serializable {

    @NotBlank
    private String episodeId;
    @NotNull
    private Double time;

    public UserWatchProgressRequest(String episodeId, Double time) {
        this.episodeId = episodeId;
        this.time = time;
    }
}
