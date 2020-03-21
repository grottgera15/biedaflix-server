package bestworkingconditions.biedaflix.server.vod.episode;

import bestworkingconditions.biedaflix.server.vod.episode.model.Episode;
import bestworkingconditions.biedaflix.server.vod.episode.model.response.EpisodeFullResponse;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EpisodeFullResponsePostProcessor {

    private final EpisodeRepository episodeRepository;
    private final EpisodeMapper episodeMapper;

    @Autowired
    public EpisodeFullResponsePostProcessor(EpisodeRepository episodeRepository,@Lazy EpisodeMapper episodeMapper) {
        this.episodeRepository = episodeRepository;
        this.episodeMapper = episodeMapper;
    }

    @AfterMapping
    public void getNextEpisode(@MappingTarget EpisodeFullResponse episode){

        Optional<Episode> match = episodeRepository.findBySeriesIdAndSeasonNumberAndEpisodeNumber(episode.getSeriesId(),episode.getSeasonNumber(),episode.getEpisodeNumber()+1);

        if(match.isPresent()){
            episode.setNextEpisode(episodeMapper.episodeLightResponseFromEpisode(match.get()));
        }else{
            match = episodeRepository.findBySeriesIdAndSeasonNumberAndEpisodeNumber(episode.getSeriesId(),episode.getSeasonNumber()+1,1);

            if(match.isPresent()){
                episode.setNextEpisode(episodeMapper.episodeLightResponseFromEpisode(match.get()));
            }
        }
    }

}
