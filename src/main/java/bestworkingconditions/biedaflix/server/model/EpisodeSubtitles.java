package bestworkingconditions.biedaflix.server.model;

import javax.validation.constraints.NotNull;

public class EpisodeSubtitles extends EpisodeMediaFiles {

    private Episode.SubtitlesLanguage language;

    public EpisodeSubtitles() {
    }

    public EpisodeSubtitles(String extension, @NotNull String seriesName, int season, int episode, Episode.SubtitlesLanguage language) {
        super(extension, seriesName, season, episode);
        this.language = language;
    }

    @Override
    public String getFilePath() {
        return super.getFilePath() + "sub"+language+fileExtension;
    }
}
