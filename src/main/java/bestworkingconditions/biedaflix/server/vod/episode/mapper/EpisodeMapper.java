package bestworkingconditions.biedaflix.server.vod.episode.mapper;

import bestworkingconditions.biedaflix.server.vod.episode.model.Episode;
import bestworkingconditions.biedaflix.server.vod.episode.model.response.EpisodeLightResponse;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EpisodeMapper {
    EpisodeLightResponse episodeLightResponseFromEpisode(Episode episode);
    List<EpisodeLightResponse> episodeLightResponseListFromEpisodeList(List<Episode> episodes);
}
