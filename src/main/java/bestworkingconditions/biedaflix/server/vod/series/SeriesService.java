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

    private final EpisodeRepository episodeRepository;
    private final EpisodeService episodeService;
    private final SeriesRepository seriesRepository;

    @Autowired
    public SeriesService(AppProperties appProperties, StoreProperties storeProperties, EpisodeRepository episodeRepository, EpisodeService episodeService, SeriesRepository seriesRepository) {
        this.episodeRepository = episodeRepository;
        this.episodeService = episodeService;
        this.seriesRepository = seriesRepository;
    }
/*
    public void deleteSeries(String seriesId){

        List<Episode> episodes = episodeRepository.findAllBySeriesId(seriesId);

        for (Episode e : episodes){
            episodeService.deleteEpisode(e.getId());
        }



        seriesRepository.deleteById(seriesId);
    }


 */
}
