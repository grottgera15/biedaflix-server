package bestworkingconditions.biedaflix.server.vod.series;

import bestworkingconditions.biedaflix.server.vod.series.model.Series;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesRequest;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/series")
public class SeriesController {

    private final SeriesRepository seriesRepository;
    private final SeriesService seriesService;
    private final SeriesMapper mapper;

    @Autowired
    public SeriesController(SeriesRepository seriesRepository, SeriesService seriesService, SeriesMapper mapper) {
        this.seriesRepository = seriesRepository;
        this.seriesService = seriesService;
        this.mapper = mapper;
    }

    @PostMapping(value = "/{id}/banner",consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> addBanner(@PathVariable String id ,@Valid @NotNull @RequestParam MultipartFile banner){
        return ResponseEntity.ok(mapper.seriesLightResponseFromSeries(seriesService.setBanner(id,banner)));
    }

    @PostMapping(value = "/{id}/logo", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> addLogo(@PathVariable String id ,@Valid @NotNull @RequestParam MultipartFile logo){
        return ResponseEntity.ok(mapper.seriesLightResponseFromSeries(seriesService.setLogo(id,logo)));
    }

    @PostMapping(value = "", consumes = {"application/json"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> AddSeries(@Valid SeriesRequest request){

        Series createdSeries = seriesService.create(mapper.seriesFromSeriesRequest(request));
        return new ResponseEntity<>(mapper.seriesLightResponseFromSeries(createdSeries),HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}", consumes = {"application/json"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> patchSeries(@PathVariable String id, @RequestBody SeriesRequest request) {
        Series s = seriesService.update(seriesService.fetchAndUpdateSeries(id,request));
        return ResponseEntity.ok(mapper.seriesLightResponseFromSeries(s));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSeries(
            @PathVariable("id") String id,
            @RequestParam(required = false, defaultValue = "false") Boolean showSeasons){

        Series requestedSeries = seriesService.findById(id);

        if(showSeasons){
            return ResponseEntity.ok(mapper.seriesFullResponseFromSeries(requestedSeries));
        }else{
            return  ResponseEntity.ok(mapper.seriesLightResponseFromSeries(requestedSeries));
        }
    }

    @GetMapping("")
    public ResponseEntity<?> GetAll(
            @RequestParam(required = false, defaultValue = "false") Boolean showSeasons,
            @RequestParam(required = false) Optional<SeriesStatus> status,
            @RequestParam(required = false) Optional<String> sourceId
    ) {

        Series example = new Series();
        status.ifPresent(example::setStatus);
        sourceId.ifPresent(example::setSourceId);

        List<Series> requestedSeries = seriesRepository.findAll(Example.of(example));

        if(showSeasons){
            return ResponseEntity.ok(mapper.seriesFullResponseFromSeries(requestedSeries));
        }else{
            return  ResponseEntity.ok(mapper.seriesLightResponseFromSeries(requestedSeries));
        }
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> deleteSeries(@PathVariable String id){
        seriesService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}

