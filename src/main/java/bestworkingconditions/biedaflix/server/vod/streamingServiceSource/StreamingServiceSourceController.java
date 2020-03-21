package bestworkingconditions.biedaflix.server.vod.streamingServiceSource;

import bestworkingconditions.biedaflix.server.vod.series.SeriesMapper;
import bestworkingconditions.biedaflix.server.vod.series.SeriesRepository;
import bestworkingconditions.biedaflix.server.vod.series.model.Series;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesLightResponse;
import bestworkingconditions.biedaflix.server.vod.streamingServiceSource.model.StreamingServiceSource;
import bestworkingconditions.biedaflix.server.vod.streamingServiceSource.model.StreamingServiceSourceRequest;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/streamingSources")
public class StreamingServiceSourceController {

    private final StreamingServiceSourceRepository repository;
    private final StreamingServiceSourceMapper mapper;
    private final SeriesMapper seriesMapper;
    private final SeriesRepository seriesRepository;
    private final StreamingServiceSourceService streamingServiceSourceService;

    @Autowired
    public StreamingServiceSourceController(StreamingServiceSourceRepository repository, StreamingServiceSourceMapper mapper, SeriesMapper seriesMapper, SeriesRepository seriesRepository, StreamingServiceSourceService streamingServiceSourceService) {
        this.repository = repository;
        this.mapper = mapper;
        this.seriesMapper = seriesMapper;
        this.seriesRepository = seriesRepository;
        this.streamingServiceSourceService = streamingServiceSourceService;
    }


    @PostMapping("/{id}/logo")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SOURCES')")
    public ResponseEntity<?> addLogoFile(
            @PathVariable String id,
            @RequestParam(name = "file") MultipartFile file){
        return ResponseEntity.ok(mapper.toDTO(streamingServiceSourceService.setLogo(id,file)));
    }

    @PostMapping(value = "", consumes = {"application/json"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SOURCES')")
    public ResponseEntity<?> addStreamingServiceSource(@Valid @RequestBody StreamingServiceSourceRequest request) {

        StreamingServiceSource source = streamingServiceSourceService.create(mapper.streamingServiceSourceFromRequest(request));
        return new ResponseEntity<>(mapper.toDTO(streamingServiceSourceService.create(source)),HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}", consumes = {"application/json"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SOURCES')")
    public ResponseEntity<?> updateStreamingServiceSource( @PathVariable String id,
                                                            @Valid @RequestBody StreamingServiceSourceRequest request) {

        StreamingServiceSource requested = streamingServiceSourceService.findById(id);
        requested.setName(request.getName());

        return new ResponseEntity<>(mapper.toDTO(repository.save(requested)),HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getListOfAllStreamingServiceSources(){
        return ResponseEntity.ok(mapper.toDTOList(streamingServiceSourceService.findAll()));
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SOURCES')")
    public ResponseEntity<?> deleteStreamingServiceSource(@PathVariable String id) {

        List<Series> associatedSeries = seriesRepository.findAllBySourceId(id);

        if(associatedSeries.size() > 0){

            List<SeriesLightResponse> lightResponses = seriesMapper.seriesLightResponseListFromSeriesList(associatedSeries);

            JSONObject response = new JSONObject();
            response.put("message","you cannot delete this StreamingSource, as it is used in the following associatedSeries");
            response.put("associatedSeries", lightResponses);

            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }else{
            repository.deleteById(id);
        }

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
