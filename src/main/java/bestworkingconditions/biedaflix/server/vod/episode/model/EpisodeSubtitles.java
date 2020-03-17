package bestworkingconditions.biedaflix.server.vod.episode.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
public class EpisodeSubtitles {
    private Locale language;
}
