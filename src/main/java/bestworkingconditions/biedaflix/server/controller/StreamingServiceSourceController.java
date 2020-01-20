package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.StreamingServiceSource;
import bestworkingconditions.biedaflix.server.model.response.StreamingServiceSourceResponse;
import bestworkingconditions.biedaflix.server.repository.FileContentStore;
import bestworkingconditions.biedaflix.server.repository.FileResourceContentStore;
import bestworkingconditions.biedaflix.server.repository.StreamingServiceSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class StreamingServiceSourceController {

    private final FileResourceContentStore contentStore;
    private final StreamingServiceSourceRepository repository;

    @Autowired
    public StreamingServiceSourceController(FileResourceContentStore contentStore, StreamingServiceSourceRepository repository) {this.contentStore = contentStore;
        this.repository = repository;
    }

    @PostMapping(value = "/streamingSource", consumes = {"multipart/form-data"})
    public ResponseEntity<?> addStreamingServiceSource(@RequestParam(name="name") String name, @RequestParam(name="logo")MultipartFile logo) throws IOException {

        List<StreamingServiceSource> streamingServiceSources = repository.findAll();

        if(streamingServiceSources.stream().anyMatch(t-> t.getName().equals(name)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"streamingSource of given name already exists in the database!");

        StreamingServiceSource newSource = new StreamingServiceSource();
        newSource.setName(name);


        contentStore.setContent(newSource,logo.getInputStream());
        repository.save(newSource);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/streamingSource")
    public ResponseEntity<?> getListOfAllStreamingServiceSources(){
        List<StreamingServiceSource> streamingServiceSources = repository.findAll();
        List<StreamingServiceSourceResponse> response = new ArrayList<>();

        for(StreamingServiceSource source : streamingServiceSources){
            response.add(new StreamingServiceSourceResponse(source.getId(),source.getName(),source.getResourceURI()));
        }

        return ResponseEntity.ok(response);
    }

}
