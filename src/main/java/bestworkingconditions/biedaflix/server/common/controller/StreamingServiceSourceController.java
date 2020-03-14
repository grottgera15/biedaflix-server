package bestworkingconditions.biedaflix.server.common.controller;

import bestworkingconditions.biedaflix.server.series.Series;
import bestworkingconditions.biedaflix.server.common.model.StreamingServiceSource;
import bestworkingconditions.biedaflix.server.series.SeriesLightResponse;
import bestworkingconditions.biedaflix.server.common.model.response.StreamingServiceSourceResponse;
import bestworkingconditions.biedaflix.server.common.properties.AppProperties;
import bestworkingconditions.biedaflix.server.common.repository.FileResourceContentStore;
import bestworkingconditions.biedaflix.server.series.SeriesRepository;
import bestworkingconditions.biedaflix.server.common.repository.StreamingServiceSourceRepository;
import bestworkingconditions.biedaflix.server.series.SeriesService;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class StreamingServiceSourceController {

    private final FileResourceContentStore contentStore;
    private final StreamingServiceSourceRepository repository;
    private final SeriesRepository seriesRepository;
    private final SeriesService seriesService;
    private final AppProperties appProperties;

    @Autowired
    public StreamingServiceSourceController(FileResourceContentStore contentStore, StreamingServiceSourceRepository repository, SeriesRepository seriesRepository, SeriesService seriesService, AppProperties appProperties) {this.contentStore = contentStore;
        this.repository = repository;
        this.seriesRepository = seriesRepository;
        this.seriesService = seriesService;
        this.appProperties = appProperties;
    }

    private URL getStreamingServiceURL(StreamingServiceSource source) throws MalformedURLException {
        String url = new StringBuilder().append(appProperties.getApiDomain()).append("files").append(source.getFilePath()).toString();
        return new  URL(url);
    }

    private void checkIfNameIsAvailable(String name,Optional<String> requestId){

        Optional<StreamingServiceSource> match = repository.findByName(name);

        if(match.isPresent()){
           if(!match.get().getId().equals(requestId)){
               throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"streamingSource of given name already exists in the database!");
           }
        }
    }

    @PostMapping(value = "/streamingSources", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SOURCES')")
    public ResponseEntity<?> addStreamingServiceSource(@RequestParam(name="name") String name, @RequestParam(name="logo")MultipartFile logo) throws IOException {
        checkIfNameIsAvailable(name,Optional.empty());

        StreamingServiceSource newSource = repository.save(new StreamingServiceSource(FilenameUtils.getExtension(logo.getOriginalFilename()), name));

        contentStore.setContent(newSource, logo.getInputStream());

        return new ResponseEntity<>(new StreamingServiceSourceResponse(newSource.getId(),newSource.getName(),getStreamingServiceURL(newSource)),HttpStatus.CREATED);
    }

    @PatchMapping(value = "/streamingSources/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SOURCES')")
    public ResponseEntity<?> updateStreamingServiceSource( @PathVariable String id,
                                                          @RequestParam(name = "name") Optional<String> name,
                                                          @RequestParam(name = "logo") Optional<MultipartFile> logo) throws IOException{

        StreamingServiceSource source = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "StreamingServiceSource of given id does not exist!"));

        if(name.isPresent()){
            checkIfNameIsAvailable(name.get(),Optional.of(id));
            source.setName(name.get());
        }

        if(logo.isPresent()){
            contentStore.setContent(source,logo.get().getInputStream());
        }

        source = repository.save(source);
        return new ResponseEntity<>(new StreamingServiceSourceResponse(source.getId(),source.getName(),getStreamingServiceURL(source)),HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/streamingSources/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SOURCES')")
    public ResponseEntity<?> deleteStreamingServiceSource(@PathVariable String id) {

        List<Series> associatedSeries = seriesRepository.findAllByStreamingServiceId(id);

        if(associatedSeries.size() > 0){

            List<SeriesLightResponse> lightResponses = new ArrayList<>();

            for(Series s : associatedSeries){
                SeriesLightResponse lightResponse = seriesService.seriesLightResponseFromSeries(s);
                lightResponses.add(lightResponse);
            }

            JSONObject response = new JSONObject();
            response.put("message","you cannot delete this StreamingSource, as it is used in the following associatedSeries");
            response.put("associatedSeries", lightResponses);

            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }else{
            repository.deleteById(id);
        }

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/streamingSources")
    public ResponseEntity<?> getListOfAllStreamingServiceSources() throws MalformedURLException {
        List<StreamingServiceSource> streamingServiceSources = repository.findAll();
        List<StreamingServiceSourceResponse> response = new ArrayList<>();

        for(StreamingServiceSource source : streamingServiceSources){
            response.add(new StreamingServiceSourceResponse(source.getId(),source.getName(),getStreamingServiceURL(source)));
        }

        return ResponseEntity.ok(response);
    }

}
