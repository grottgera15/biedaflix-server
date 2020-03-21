package bestworkingconditions.biedaflix.server.vod.episode.mapper;

import bestworkingconditions.biedaflix.server.vod.episode.model.Episode;
import bestworkingconditions.biedaflix.server.vod.episode.model.response.EpisodeLightResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EpisodeMapper {

    @BeanMapping(resultType = EpisodeLightResponse.class)
    EpisodeLightResponse episodeLightResponseFromEpisode(Episode episode);

    @IterableMapping(elementTargetType = EpisodeLightResponse.class)
    List<EpisodeLightResponse> episodeLightResponseFromEpisode(List<Episode> episodes);
}
