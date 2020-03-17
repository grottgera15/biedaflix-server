package bestworkingconditions.biedaflix.server.vod.series;

import bestworkingconditions.biedaflix.server.vod.episode.model.Episode;
import bestworkingconditions.biedaflix.server.vod.episode.model.response.EpisodeLightResponse;
import bestworkingconditions.biedaflix.server.common.model.response.MediaFilesResponse;
import bestworkingconditions.biedaflix.server.common.properties.AppProperties;
import bestworkingconditions.biedaflix.server.common.properties.StoreProperties;
import bestworkingconditions.biedaflix.server.vod.episode.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.vod.episode.service.EpisodeService;
import bestworkingconditions.biedaflix.server.vod.series.model.Series;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesFullResponse;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesLightResponse;
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
