package bestworkingconditions.biedaflix.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class EpisodeThumbs extends EpisodeMediaFile {

    private String thumbName;


    public EpisodeThumbs(String extension, @NotNull String seriesId, String episodeId, String thumbName) {
        super(extension, seriesId, episodeId);
        this.thumbName = thumbName;
    }

    @Override
    public String getFilePath() {
        return super.getFilePath() + "thumbs/" + thumbName +"."+ fileExtension;
    }
}
