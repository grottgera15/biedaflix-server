package bestworkingconditions.biedaflix.server.vod.series;

import bestworkingconditions.biedaflix.server.file.FileResourceMapper;
import bestworkingconditions.biedaflix.server.vod.episode.mapper.EpisodeMapper;
import bestworkingconditions.biedaflix.server.vod.episode.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.vod.series.model.Series;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesFullResponse;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesLightResponse;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring",uses = FileResourceMapper.class)
public abstract class SeriesMapper {

    private final EpisodeRepository episodeRepository;
    private final EpisodeMapper episodeMapper;

    @Autowired
    protected SeriesMapper(EpisodeRepository episodeRepository, EpisodeMapper episodeMapper) {
        this.episodeRepository = episodeRepository;
        this.episodeMapper = episodeMapper;
    }

    public abstract SeriesLightResponse seriesLightResponseFromSeries(Series series);
    public abstract List<SeriesLightResponse> seriesLightResponseListFromSeriesList(List<Series> seriesList);

    public abstract Series seriesFromSeriesRequest(SeriesRequest request);

    @AfterMapping
    protected void addEpisodes(@MappingTarget SeriesFullResponse fullResponse){
        fullResponse.setEpisodes(
                episodeMapper.episodeLightResponseListFromEpisodeList(
                        episodeRepository.findAllBySeriesIdOrderBySeasonNumberAscEpisodeNumberAsc(fullResponse.getId())
                )
        );
    }
}
