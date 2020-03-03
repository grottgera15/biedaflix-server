package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.*;
import bestworkingconditions.biedaflix.server.model.request.SeriesRequest;
import bestworkingconditions.biedaflix.server.model.response.EpisodeLightResponse;
import bestworkingconditions.biedaflix.server.model.response.MediaFilesResponse;
import bestworkingconditions.biedaflix.server.model.response.SeriesFullResponse;
import bestworkingconditions.biedaflix.server.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.repository.FileResourceContentStore;
import bestworkingconditions.biedaflix.server.repository.SeriesRepository;
import bestworkingconditions.biedaflix.server.service.SeriesService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

@RestController
public class SeriesController {

    private final SeriesRepository seriesRepository;
    private final EpisodeRepository episodeRepository;
    private final FileResourceContentStore fileResourceContentStore;
    private final SeriesService seriesService;

    @Autowired
    public SeriesController(SeriesRepository seriesRepository, EpisodeRepository episodeRepository, FileResourceContentStore fileResourceContentStore, SeriesService seriesService) {
        this.seriesRepository = seriesRepository;
        this.episodeRepository = episodeRepository;
        this.fileResourceContentStore = fileResourceContentStore;
        this.seriesService = seriesService;
    }


    @GetMapping("/series")
    public ResponseEntity<List<SeriesFullResponse>> GetAll() throws MalformedURLException {
        List<Series> availableSeries = seriesRepository.findAll();

        List<SeriesFullResponse> response = new ArrayList<>();

        for(Series series : availableSeries){

            Map<Integer,List<EpisodeLightResponse>> seasonsResponse = new HashMap<>();
            List<Episode> seriesEpisodes = episodeRepository.findAllBySeriesIdOrderByEpisodeNumber(series.getId());

            if(seriesEpisodes.size() == 0){
                response.add(new SeriesFullResponse(series.getId(),
                        series.getName(),
                        series.getDescription(),
                        new MediaFilesResponse(seriesService.getSeriesResourceURL(series.getSeriesBanner().getFilePath())),
                        new MediaFilesResponse(seriesService.getSeriesResourceURL(series.getLogo().getFilePath())),
                        series.getStreamingServiceId(),
                        series.getOnGoing(),
                        seasonsResponse
                ));
            }
            else{
                for (Episode ep : seriesEpisodes){

                    int seasonNumber = ep.getSeasonNumber();

                    EpisodeLightResponse episodeLightResponse = new EpisodeLightResponse(
                            ep.getId(),
                            ep.getEpisodeNumber(),
                            ep.getName(),
                            ep.isAvailable(),
                            ep.getReleaseDate()
                    );

                    if(!seasonsResponse.containsKey(seasonNumber))
                        seasonsResponse.put(seasonNumber,new ArrayList<>());

                    seasonsResponse.get(seasonNumber).add(episodeLightResponse);


                }

                response.add(new SeriesFullResponse(series.getId(),
                        series.getName(),
                        series.getDescription(),
                        new MediaFilesResponse(seriesService.getSeriesResourceURL(series.getSeriesBanner().getFilePath())),
                        new MediaFilesResponse(seriesService.getSeriesResourceURL(series.getLogo().getFilePath())),
                        series.getStreamingServiceId(),
                        series.getOnGoing(),
                        seasonsResponse
                ));
            }
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/series")
    public ResponseEntity<Series> AddSeries(@Valid SeriesRequest request,
                                            @RequestParam(name = "banner", required = false) Optional<MultipartFile> banner,
                                            @RequestParam(name = "logo", required = false) Optional<MultipartFile> logo) throws IOException {

        List<Series> all = seriesRepository.findAll();

        if(all.stream().anyMatch(t -> t.getName().equals(request.getName())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Series of given name already exists in database");

        Series newSeries = new Series();
        newSeries.setName(request.getName());
        newSeries.setDescription(request.getDescription());
        newSeries.setOnGoing(request.getOnGoing());
        newSeries.setStreamingServiceId(request.getSourceId());

        if(logo.isPresent()){
            SeriesLogo seriesLogo = new SeriesLogo(FilenameUtils.getExtension(logo.get().getOriginalFilename()),newSeries.getFolderName());
            newSeries.setLogo(seriesLogo);


            fileResourceContentStore.setContent(seriesLogo,logo.get().getInputStream());
        }

        if(banner.isPresent()){
            SeriesBanner seriesBanner = new SeriesBanner(FilenameUtils.getExtension(banner.get().getOriginalFilename()),newSeries.getFolderName());
            newSeries.setSeriesBanner(seriesBanner);

            fileResourceContentStore.setContent(seriesBanner,banner.get().getInputStream());
        }

        seriesRepository.save(newSeries);

        return ResponseEntity.ok(newSeries);
    }
}

