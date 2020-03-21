package bestworkingconditions.biedaflix.server.vod.series;

import bestworkingconditions.biedaflix.server.vod.episode.mapper.EpisodeMapper;
import bestworkingconditions.biedaflix.server.vod.episode.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesFullResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SeriesFullResponsePostProcessor {

    private final EpisodeMapper episodeMapper;
    private final EpisodeRepository episodeRepository;

    @Autowired
    public SeriesFullResponsePostProcessor(EpisodeMapper episodeMapper, EpisodeRepository episodeRepository) {
        this.episodeMapper = episodeMapper;
        this.episodeRepository = episodeRepository;
    }

    @AfterMapping
    protected void addEpisodes(@MappingTarget SeriesFullResponse fullResponse){
        fullResponse.setEpisodes(
                episodeMapper.episodeLightResponseListFromEpisodeList(
                        episodeRepository.findAllBySeriesIdOrderBySeasonNumberAscEpisodeNumberAsc(fullResponse.getId())
                )
        );
    }
}
