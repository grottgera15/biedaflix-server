package bestworkingconditions.biedaflix.server.service;

import bestworkingconditions.biedaflix.server.model.Series;
import bestworkingconditions.biedaflix.server.model.SeriesMediaFile;
import bestworkingconditions.biedaflix.server.model.response.EpisodeLightResponse;
import bestworkingconditions.biedaflix.server.model.response.MediaFilesResponse;
import bestworkingconditions.biedaflix.server.model.response.SeriesFullResponse;
import bestworkingconditions.biedaflix.server.model.response.SeriesLightResponse;
import bestworkingconditions.biedaflix.server.properties.AppProperties;
import bestworkingconditions.biedaflix.server.properties.StoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Service
public class SeriesService {

    private final AppProperties appProperties;
    private final StoreProperties storeProperties;

    @Autowired
    public SeriesService(AppProperties appProperties, StoreProperties storeProperties) {this.appProperties = appProperties;
        this.storeProperties = storeProperties;
    }

    public String getSeriesResourceURL(SeriesMediaFile mediaFile) {

        String url = null;

        if(mediaFile != null){
            url = appProperties.getApiDomain() + storeProperties.getPath() + mediaFile.getFilePath();
        }

        return url;
    }

    public SeriesFullResponse seriesFullResponsefromSeries(Series series, Map<Integer, List<EpisodeLightResponse>> seasonsResponse) {

        return new SeriesFullResponse(
                series.getId(),
                series.getName(),
                series.getDescription(),
                new MediaFilesResponse(getSeriesResourceURL(series.getSeriesBanner())),
                new MediaFilesResponse(getSeriesResourceURL(series.getLogo())),
                series.getStreamingServiceId(),
                series.getStatus(),
                seasonsResponse);
    }

    public SeriesLightResponse seriesLightResponseFromSeries(Series series) {
        return new SeriesLightResponse(
                series.getId(),
                series.getName(),
                series.getDescription(),
                new MediaFilesResponse(getSeriesResourceURL(series.getSeriesBanner())),
                new MediaFilesResponse(getSeriesResourceURL(series.getLogo())),
                series.getStreamingServiceId(),
                series.getStatus()
        );
    }
}
