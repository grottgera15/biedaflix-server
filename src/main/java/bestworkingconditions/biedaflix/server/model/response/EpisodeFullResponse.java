package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.EpisodeStatus;
import bestworkingconditions.biedaflix.server.model.EpisodeThumbs;

import javax.swing.text.html.Option;
import java.io.Serializable;
import java.util.*;

public class EpisodeFullResponse extends EpisodeLightResponse{

    private Map<String, String> videoSources = new HashMap<>();
    private Map<String,String> subtitles = new HashMap<>();
    private List<MediaFilesResponse> thumbs = new ArrayList<>();
    private Optional<EpisodeLightResponse> nextEpisode;


    public EpisodeFullResponse() {
        nextEpisode = Optional.empty();
    }

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

    public Map<String, String> getVideoSources() {
        return videoSources;
    }

    public void setVideoSources(Map<String, String> videoSources) {
        this.videoSources = videoSources;
    }

    public Map<String, String> getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(Map<String, String> subtitles) {
        this.subtitles = subtitles;
    }

    public List<MediaFilesResponse> getThumbs() {
        return thumbs;
    }

    public void setThumbs(List<MediaFilesResponse> thumbs) {
        this.thumbs = thumbs;
    }

    public Optional<EpisodeLightResponse> getNextEpisode() {
        return nextEpisode;
    }

    public void setNextEpisode(Optional<EpisodeLightResponse> nextEpisode) {
        this.nextEpisode = nextEpisode;
    }
}
