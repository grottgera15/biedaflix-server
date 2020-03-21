package bestworkingconditions.biedaflix.server.vod.series;

import bestworkingconditions.biedaflix.server.file.FileResourceMapper;
import bestworkingconditions.biedaflix.server.vod.series.model.Series;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesLightResponse;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = {FileResourceMapper.class,SeriesFullResponsePostProcessor.class})
public interface SeriesMapper {
    SeriesLightResponse seriesLightResponseFromSeries(Series series);
    List<SeriesLightResponse> seriesLightResponseListFromSeriesList(List<Series> seriesList);
    Series seriesFromSeriesRequest(SeriesRequest request);
}
