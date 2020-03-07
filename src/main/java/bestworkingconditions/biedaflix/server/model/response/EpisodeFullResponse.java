package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.EpisodeStatus;
import bestworkingconditions.biedaflix.server.model.EpisodeThumbs;

import java.io.Serializable;
import java.util.*;

public class EpisodeFullResponse extends EpisodeLightResponse{

    private Map<String, String> videoSources = new HashMap<>();
    private Map<String,String> subtitles = new HashMap<>();
    private List<MediaFilesResponse> thumbs = new ArrayList<>();

    public EpisodeFullResponse() {
    }

    public EpisodeFullResponse(String id, int episodeNumber, String name, EpisodeStatus status, Date releaseDate, Map<String, String> videoSources, Map<String, String> subtitles, List<MediaFilesResponse> thumbs) {
        super(id, episodeNumber, name, status, releaseDate);
        this.videoSources = videoSources;
        this.subtitles = subtitles;
        this.thumbs = thumbs;
    }

    public EpisodeFullResponse(Episode episode, Map<String, String> videoSources, Map<String, String> subtitles, List<MediaFilesResponse> thumbs) {
        super(episode);
        this.videoSources = videoSources;
        this.subtitles = subtitles;
        this.thumbs = thumbs;
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
}
