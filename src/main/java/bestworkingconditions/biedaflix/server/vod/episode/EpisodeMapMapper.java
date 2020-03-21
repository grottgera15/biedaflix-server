package bestworkingconditions.biedaflix.server.vod.episode;

import bestworkingconditions.biedaflix.server.file.FileResource;
import bestworkingconditions.biedaflix.server.file.FileResourceMapper;
import bestworkingconditions.biedaflix.server.vod.episode.model.VideoQuality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
public class EpisodeMapMapper {

    private final FileResourceMapper fileResourceMapper;

    @Autowired
    public EpisodeMapMapper(FileResourceMapper fileResourceMapper) {this.fileResourceMapper = fileResourceMapper;}

    public Map<String, URL> mapFromVideos(Map<VideoQuality, FileResource> videos){
        Map<String,URL> result  = new HashMap<>();

        for(Map.Entry<VideoQuality,FileResource> entry : videos.entrySet()){
            result.put(entry.getKey().toString(),fileResourceMapper.mapToURL(entry.getValue()));
        }

        return result;
    }

    public Map<String,URL> mapFromSubtitles(Map<String, FileResource> subtitles){
        Map<String,URL> result = new HashMap<>();

        for(Map.Entry<String,FileResource> entry : subtitles.entrySet()){
            result.put(entry.getKey(),fileResourceMapper.mapToURL(entry.getValue()));
        }

        return result;
    }
}
