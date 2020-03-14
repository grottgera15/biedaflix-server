package bestworkingconditions.biedaflix.server.vod.episode.model.response;

import bestworkingconditions.biedaflix.server.common.model.response.MediaFilesResponse;
import bestworkingconditions.biedaflix.server.vod.episode.model.Episode;
import bestworkingconditions.biedaflix.server.vod.episode.model.EpisodeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class EpisodeFullResponse extends EpisodeLightResponse{

    private Map<String, String> videoSources = new HashMap<>();
    private Map<String,String> subtitles = new HashMap<>();
    private List<MediaFilesResponse> thumbs = new ArrayList<>();
    private Optional<EpisodeLightResponse> nextEpisode = Optional.empty();

    public EpisodeFullResponse(String id, int episodeNumber, int episodeSeason, String name, EpisodeStatus status, Date releaseDate, Map<String, String> videoSources, Map<String, String> subtitles, List<MediaFilesResponse> thumbs, Optional<EpisodeLightResponse> nextEpisode) {
        super(id, episodeNumber, episodeSeason, name, status, releaseDate);
        this.videoSources = videoSources;
        this.subtitles = subtitles;
        this.thumbs = thumbs;
        this.nextEpisode = nextEpisode;
    }

    public EpisodeFullResponse(Episode episode, Map<String, String> videoSources, Map<String, String> subtitles, List<MediaFilesResponse> thumbs, Optional<Episode> nextEpisode) {
        super(episode);
        this.videoSources = videoSources;
        this.subtitles = subtitles;
        this.thumbs = thumbs;

        nextEpisode.ifPresent(x -> this.nextEpisode = Optional.of(new EpisodeLightResponse(x)));
    }
}
