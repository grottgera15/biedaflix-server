package bestworkingconditions.biedaflix.server.vod.series;

import bestworkingconditions.biedaflix.server.file.FileResourceMapper;
import bestworkingconditions.biedaflix.server.vod.series.model.Series;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesFullResponse;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesLightResponse;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesRequest;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",uses = {FileResourceMapper.class,SeriesFullResponsePostProcessor.class})
public interface SeriesMapper {

    @BeanMapping(resultType = SeriesLightResponse.class)
    SeriesLightResponse seriesLightResponseFromSeries(Series series);

    @IterableMapping(elementTargetType = SeriesLightResponse.class)
    List<SeriesLightResponse> seriesLightResponseFromSeries(List<Series> seriesList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "logo", ignore = true)
    @Mapping(target = "banner", ignore = true)
    Series seriesFromSeriesRequest(SeriesRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "logo", ignore = true)
    @Mapping(target = "banner", ignore = true)
    Series updateSeriesFromSeriesRequest(SeriesRequest request, @MappingTarget Series series);

    @Mapping(target = "episodes", ignore = true)

    @BeanMapping(resultType = SeriesFullResponse.class)
    SeriesFullResponse seriesFullResponseFromSeries(Series series);

    @IterableMapping(elementTargetType = SeriesFullResponse.class)
    List<SeriesFullResponse> seriesFullResponseFromSeries(List<Series> seriesList);
}
