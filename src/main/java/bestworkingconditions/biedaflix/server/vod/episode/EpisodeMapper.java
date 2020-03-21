package bestworkingconditions.biedaflix.server.vod.episode;

import bestworkingconditions.biedaflix.server.file.FileResourceMapper;
import bestworkingconditions.biedaflix.server.vod.episode.model.Episode;
import bestworkingconditions.biedaflix.server.vod.episode.model.request.EpisodeRequest;
import bestworkingconditions.biedaflix.server.vod.episode.model.response.EpisodeFullResponse;
import bestworkingconditions.biedaflix.server.vod.episode.model.response.EpisodeLightResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EpisodeMapMapper.class,FileResourceMapper.class})
public interface EpisodeMapper {

    @BeanMapping(resultType = EpisodeLightResponse.class)
    EpisodeLightResponse episodeLightResponseFromEpisode(Episode episode);

    @IterableMapping(elementTargetType = EpisodeLightResponse.class)
    List<EpisodeLightResponse> episodeLightResponseFromEpisode(List<Episode> episodes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "size", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "subtitles", ignore = true)
    @Mapping(target = "thumbs", ignore = true)
    @Mapping(target = "videos", ignore = true)
    Episode episodeFromEpisodeRequest(EpisodeRequest request);

    @Mapping(target = "nextEpisode", ignore = true)
    EpisodeFullResponse episodeFullResponseFromEpisode(Episode episode);

    List<EpisodeFullResponse> episodeFullResponseFromEpisode(List<Episode> episodes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "size", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "subtitles", ignore = true)
    @Mapping(target = "thumbs", ignore = true)
    @Mapping(target = "videos", ignore = true)
    Episode updateEpisodeFromEpisodeRequest(EpisodeRequest request, @MappingTarget Episode episode);
}
