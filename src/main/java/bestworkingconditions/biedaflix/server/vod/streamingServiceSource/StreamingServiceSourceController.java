package bestworkingconditions.biedaflix.server.vod.streamingServiceSource;

import bestworkingconditions.biedaflix.server.file.FileResourceContentStore;
import bestworkingconditions.biedaflix.server.file.FileResourceRepository;
import bestworkingconditions.biedaflix.server.common.properties.AppProperties;
import bestworkingconditions.biedaflix.server.vod.series.SeriesRepository;
import bestworkingconditions.biedaflix.server.vod.series.SeriesService;
import bestworkingconditions.biedaflix.server.vod.series.model.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/streamingSources")
public class StreamingServiceSourceController {

    private final FileResourceContentStore contentStore;
    private final FileResourceRepository fileResourceRepository;
    private final StreamingServiceSourceRepository repository;
    private final StreamingServiceSourceMapper mapper;
    private final SeriesRepository seriesRepository;
    private final StreamingServiceSourceService streamingServiceSourceService;
    private final SeriesService seriesService;
    private final AppProperties appProperties;

    @Autowired
    public StreamingServiceSourceController(FileResourceContentStore contentStore, FileResourceRepository fileResourceRepository, StreamingServiceSourceRepository repository, StreamingServiceSourceMapper mapper, SeriesRepository seriesRepository, StreamingServiceSourceService streamingServiceSourceService, SeriesService seriesService, AppProperties appProperties) {this.contentStore = contentStore;
        this.fileResourceRepository = fileResourceRepository;
        this.repository = repository;
        this.mapper = mapper;
        this.seriesRepository = seriesRepository;
        this.streamingServiceSourceService = streamingServiceSourceService;
        this.seriesService = seriesService;
        this.appProperties = appProperties;
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
    public ResponseEntity<?> addStreamingServiceSource(@RequestBody String name) {

        StreamingServiceSource source = new StreamingServiceSource();
        source.setName(name);

        return new ResponseEntity<>(mapper.toDTO(streamingServiceSourceService.create(source)),HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}", consumes = {"application/json"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SOURCES')")
    public ResponseEntity<?> updateStreamingServiceSource( @PathVariable String id,
                                                            @NotBlank String name) {

        StreamingServiceSource requested = streamingServiceSourceService.findById(id);
        requested.setName(name);


        return new ResponseEntity<>(mapper.toDTO(repository.save(requested)),HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getListOfAllStreamingServiceSources(){
        return ResponseEntity.ok(mapper.toDTOList(streamingServiceSourceService.findAll()));
    }


    @DeleteMapping(value = "/streamingSources/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SOURCES')")
    public ResponseEntity<?> deleteStreamingServiceSource(@PathVariable String id) {

        List<Series> associatedSeries = seriesRepository.findAllBySourceId(id);

        if(associatedSeries.size() > 0){

            seriesLightResponses

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
