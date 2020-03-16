package bestworkingconditions.biedaflix.server.vod.episode.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class EpisodeSubtitles {

    public enum SubtitlesLanguage{
        PL("PL"),
        ENG("ENG");

        private String value;

        SubtitlesLanguage(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private SubtitlesLanguage language;
}
