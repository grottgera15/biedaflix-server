package bestworkingconditions.biedaflix.server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class EpisodeSubtitles extends EpisodeMediaFile {

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

    public EpisodeSubtitles(String extension, @NotNull String seriesId, String episodeId, SubtitlesLanguage language) {
        super(extension, seriesId, episodeId);
        this.language = language;
    }

    @Override
    public String getFilePath() {
        return super.getFilePath() + "sub"+language.getValue() + "." +fileExtension;
    }
}
