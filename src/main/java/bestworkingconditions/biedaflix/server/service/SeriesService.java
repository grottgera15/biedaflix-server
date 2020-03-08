package bestworkingconditions.biedaflix.server.service;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.Series;
import bestworkingconditions.biedaflix.server.model.SeriesMediaFile;
import bestworkingconditions.biedaflix.server.model.response.EpisodeLightResponse;
import bestworkingconditions.biedaflix.server.model.response.MediaFilesResponse;
import bestworkingconditions.biedaflix.server.model.response.SeriesFullResponse;
import bestworkingconditions.biedaflix.server.model.response.SeriesLightResponse;
import bestworkingconditions.biedaflix.server.properties.AppProperties;
import bestworkingconditions.biedaflix.server.properties.StoreProperties;
import bestworkingconditions.biedaflix.server.repository.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeriesService {

    private final AppProperties appProperties;
    private final StoreProperties storeProperties;
    private final EpisodeRepository episodeRepository;

    @Autowired
    public SeriesService(AppProperties appProperties, StoreProperties storeProperties, EpisodeRepository episodeRepository) {this.appProperties = appProperties;
        this.storeProperties = storeProperties;
        this.episodeRepository = episodeRepository;
    }

    public String getSeriesResourceURL(SeriesMediaFile mediaFile) {

        String url = null;

        if(mediaFile != null){
            url = appProperties.getApiDomain() + storeProperties.getPath() + mediaFile.getFilePath();
        }

        return url;
    }

    public Map<Integer,List<EpisodeLightResponse>> constructSeasons(Series series){
        Map<Integer,List<EpisodeLightResponse>> seasonsResponse = new HashMap<>();

        List<Episode> seriesEpisodes = episodeRepository.findAllBySeriesIdOrderByEpisodeNumber(series.getId());

        for (Episode ep : seriesEpisodes) {

            int seasonNumber = ep.getSeasonNumber();
            EpisodeLightResponse episodeLightResponse = new EpisodeLightResponse(ep);

            if (!seasonsResponse.containsKey(seasonNumber))
                seasonsResponse.put(seasonNumber, new ArrayList<>());

            seasonsResponse.get(seasonNumber)
                           .add(episodeLightResponse);
        }

        return seasonsResponse;
    }

    public SeriesFullResponse seriesFullResponseFromSeries(Series series) {

        return new SeriesFullResponse(
                series.getId(),
                series.getName(),
                series.getDescription(),
                new MediaFilesResponse(getSeriesResourceURL(series.getSeriesBanner())),
                new MediaFilesResponse(getSeriesResourceURL(series.getLogo())),
                series.getStreamingServiceId(),
                series.getStatus(),
                constructSeasons(series)
        );
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
