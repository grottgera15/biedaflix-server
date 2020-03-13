package bestworkingconditions.biedaflix.server.series;

import bestworkingconditions.biedaflix.server.series.SeriesRequest;
import bestworkingconditions.biedaflix.server.series.SeriesLightResponse;
import bestworkingconditions.biedaflix.server.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.repository.FileResourceContentStore;
import bestworkingconditions.biedaflix.server.series.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
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

    @PostMapping(value = "/series", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> AddSeries(@Valid SeriesRequest request,
                                            @RequestParam(name = "banner", required = false) Optional<MultipartFile> banner,
                                            @RequestParam(name = "logo", required = false) Optional<MultipartFile> logo) throws IOException {

        List<Series> all = seriesRepository.findAll();

        if(all.stream().anyMatch(t -> t.getName().equals(request.getName())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Series of given name already exists in database");

        Series newSeries = new Series();
        newSeries.setName(request.getName());
        newSeries.setDescription(request.getDescription());
        newSeries.setStatus(request.getStatus());
        newSeries.setStreamingServiceId(request.getSourceId());

        newSeries = seriesRepository.save(newSeries);

        if(logo.isPresent() && !logo.get().isEmpty()){
            SeriesLogo seriesLogo = new SeriesLogo(FilenameUtils.getExtension(logo.get().getOriginalFilename()),newSeries.getFolderName());
            newSeries.setLogo(seriesLogo);


            fileResourceContentStore.setContent(seriesLogo,logo.get().getInputStream());
        }

        if(banner.isPresent() && !banner.get().isEmpty()){
            SeriesBanner seriesBanner = new SeriesBanner(FilenameUtils.getExtension(banner.get().getOriginalFilename()),newSeries.getFolderName());
            newSeries.setSeriesBanner(seriesBanner);

            fileResourceContentStore.setContent(seriesBanner,banner.get().getInputStream());
        }

        newSeries = seriesRepository.save(newSeries);

        return new ResponseEntity<>(seriesService.seriesLightResponseFromSeries(newSeries),HttpStatus.CREATED);
    }

    @PatchMapping(value = "/series/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> patchSeries(
            @PathVariable String id,
            @RequestParam(required = false) Optional<String> name,
            @RequestParam(required = false) Optional<String> description,
            @RequestParam(required = false) Optional<String> sourceId,
            @RequestParam(required = false) Optional<SeriesStatus> status,
            @RequestParam(name = "banner", required = false) Optional<MultipartFile> banner,
            @RequestParam(name = "logo", required = false) Optional<MultipartFile> logo
            ) throws IOException {

        Series requestedSeries = seriesRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Series of given name does not exist in database")
        );

        name.ifPresent(requestedSeries::setName);
        description.ifPresent(requestedSeries::setDescription);
        sourceId.ifPresent(requestedSeries::setStreamingServiceId);
        status.ifPresent(requestedSeries::setStatus);


        if(banner.isPresent() && !banner.get().isEmpty()) {
            SeriesBanner seriesBanner = new SeriesBanner(FilenameUtils.getExtension(banner.get().getOriginalFilename()),requestedSeries.getFolderName());
            requestedSeries.setSeriesBanner(seriesBanner);

            fileResourceContentStore.setContent(seriesBanner,banner.get().getInputStream());
        }

        if(logo.isPresent() && !logo.get().isEmpty()){
            SeriesLogo seriesLogo = new SeriesLogo(FilenameUtils.getExtension(logo.get().getOriginalFilename()),requestedSeries.getFolderName());
            requestedSeries.setLogo(seriesLogo);

            fileResourceContentStore.setContent(seriesLogo,logo.get().getInputStream());
        }

        Series s = seriesRepository.save(requestedSeries);

        return ResponseEntity.ok(seriesService.seriesLightResponseFromSeries(s));
    }

    @GetMapping("/series/{id}")
    public ResponseEntity<?> getSeries(
            @PathVariable("id") String id,
            @RequestParam(required = false, defaultValue = "false") Boolean showSeasons){

        Series series = seriesRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Series of given name does not exist in database")
        );

        if(showSeasons){
            return ResponseEntity.ok(seriesService.seriesFullResponseFromSeries(series));
        }else{
            return  ResponseEntity.ok(seriesService.seriesLightResponseFromSeries(series));
        }

    }

    @GetMapping("/series")
    public ResponseEntity<?> GetAll(
            @RequestParam(required = false, defaultValue = "false") Boolean showSeasons,
            @RequestParam(required = false) Optional<SeriesStatus> status,
            @RequestParam(required = false) Optional<String> sourceId
    ) {

        Series example = new Series();
        status.ifPresent(example::setStatus);
        sourceId.ifPresent(example::setStreamingServiceId);

        List<Series> requestedSeries = seriesRepository.findAll(Example.of(example));

        List<SeriesLightResponse> response = new ArrayList<>();

        for(Series series : requestedSeries){

            if(showSeasons){
                response.add(seriesService.seriesFullResponseFromSeries(series));
            }else{
                response.add(seriesService.seriesLightResponseFromSeries(series));
            }
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/series/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> deleteSeries(@PathVariable String id){
        seriesService.deleteSeries(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}

