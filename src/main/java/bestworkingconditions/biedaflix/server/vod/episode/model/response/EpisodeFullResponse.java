package bestworkingconditions.biedaflix.server.vod.episode.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeFullResponse extends EpisodeLightResponse{

    private String seriesId;

    private Map<String, URL> videoSources = new HashMap<>();
    private Map<String, URL> subtitles = new HashMap<>();
    private List<URL> thumbs = new ArrayList<>();
    private EpisodeLightResponse nextEpisode;
}
