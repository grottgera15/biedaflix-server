package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.StreamingServiceSource;
import bestworkingconditions.biedaflix.server.model.response.StreamingServiceSourceResponse;
import bestworkingconditions.biedaflix.server.properties.AppProperties;
import bestworkingconditions.biedaflix.server.repository.FileResourceContentStore;
import bestworkingconditions.biedaflix.server.repository.StreamingServiceSourceRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final AppProperties appProperties;

    @Autowired
    public StreamingServiceSourceController(FileResourceContentStore contentStore, StreamingServiceSourceRepository repository, AppProperties appProperties) {this.contentStore = contentStore;
        this.repository = repository;
        this.appProperties = appProperties;
    }

    private URL getStreamingServiceURL(StreamingServiceSource source) throws MalformedURLException {
        String url = new StringBuilder().append(appProperties.getApiDomain()).append("files").append(source.getFilePath()).toString();
        return new  URL(url);
    }

    private void checkIfNameIsAvailable(String name){
        List<StreamingServiceSource> streamingServiceSources = repository.findAll();

        if(streamingServiceSources.stream().anyMatch(t-> t.getName().equals(name)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"streamingSource of given name already exists in the database!");

    }

    @PostMapping(value = "/streamingSource", consumes = {"multipart/form-data"})
    public ResponseEntity<?> addStreamingServiceSource(@RequestParam(name="name") String name, @RequestParam(name="logo")MultipartFile logo) throws IOException {
        checkIfNameIsAvailable(name);

        StreamingServiceSource newSource = new StreamingServiceSource(FilenameUtils.getExtension(logo.getOriginalFilename()), name);

        contentStore.setContent(newSource, logo.getInputStream());
        repository.save(newSource);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(value = "/streamingSource/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateStreamingServiceSource(@PathVariable String id,
                                                          @RequestParam(name="name") Optional<String> name,
                                                          @RequestParam(name="logo") Optional<MultipartFile> logo) throws IOException{

        name.ifPresent(this::checkIfNameIsAvailable);

        StreamingServiceSource source = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "StreamingServiceSource of given id does not exist!"));

        if(logo.isPresent()){
            contentStore.setContent(source,logo.get().getInputStream());
        }

        if(name.isPresent())
            source.setName(name.get());

        repository.save(source);

        return ResponseEntity.ok();
    }

    @GetMapping(value = "/streamingSource")
    public ResponseEntity<?> getListOfAllStreamingServiceSources() throws MalformedURLException {
        List<StreamingServiceSource> streamingServiceSources = repository.findAll();
        List<StreamingServiceSourceResponse> response = new ArrayList<>();

        for(StreamingServiceSource source : streamingServiceSources){
            response.add(new StreamingServiceSourceResponse(source.getId(),source.getName(),getStreamingServiceURL(source)));
        }

        return ResponseEntity.ok(response);
    }

}
