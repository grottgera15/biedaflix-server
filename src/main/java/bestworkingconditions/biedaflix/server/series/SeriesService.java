package bestworkingconditions.biedaflix.server.series;

import bestworkingconditions.biedaflix.server.episode.Episode;
import bestworkingconditions.biedaflix.server.episode.EpisodeLightResponse;
import bestworkingconditions.biedaflix.server.common.model.response.MediaFilesResponse;
import bestworkingconditions.biedaflix.server.common.properties.AppProperties;
import bestworkingconditions.biedaflix.server.common.properties.StoreProperties;
import bestworkingconditions.biedaflix.server.episode.EpisodeRepository;
import bestworkingconditions.biedaflix.server.episode.EpisodeService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SeriesService {

    private final AppProperties appProperties;
    private final StoreProperties storeProperties;
    private final EpisodeRepository episodeRepository;
    private final EpisodeService episodeService;
    private final SeriesRepository seriesRepository;

    @Autowired
    public SeriesService(AppProperties appProperties, StoreProperties storeProperties, EpisodeRepository episodeRepository, EpisodeService episodeService, SeriesRepository seriesRepository) {this.appProperties = appProperties;
        this.storeProperties = storeProperties;
        this.episodeRepository = episodeRepository;
        this.episodeService = episodeService;
        this.seriesRepository = seriesRepository;
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

    public void deleteSeries(String seriesId){

        List<Episode> episodes = episodeRepository.findAllBySeriesId(seriesId);

        for (Episode e : episodes){
            episodeService.deleteEpisode(e.getId());
        }

        File parent = new File(System.getProperty("user.dir") + "/files/series/" + seriesId);

        try {
            FileUtils.deleteDirectory(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }

        seriesRepository.deleteById(seriesId);
    }
}
