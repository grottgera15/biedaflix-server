package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;

public class EpisodeSubtitles extends EpisodeMediaFiles {

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

    public EpisodeSubtitles() {
    }

    public EpisodeSubtitles(String extension, @NotNull String seriesName, int season, int episode, SubtitlesLanguage language) {
        super(extension, seriesName, season, episode);
        this.language = language;
    }

    @Override
    public String getFilePath() {
        return super.getFilePath() + "sub"+language+fileExtension;
    }
}
