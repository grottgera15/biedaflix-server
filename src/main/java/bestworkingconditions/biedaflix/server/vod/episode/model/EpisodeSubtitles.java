package bestworkingconditions.biedaflix.server.vod.episode.model;

import bestworkingconditions.biedaflix.server.file.FileResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeSubtitles extends FileResource {
    private Locale language;
}
