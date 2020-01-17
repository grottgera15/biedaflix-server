package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.StreamingServiceSource;
import bestworkingconditions.biedaflix.server.repository.StreamingServiceSourceContentStore;
import bestworkingconditions.biedaflix.server.repository.StreamingServiceSourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.content.commons.annotations.HandleBeforeUnassociate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class StreamingServiceSourceController {

    private final StreamingServiceSourceContentStore contentStore;
    private final StreamingServiceSourceRepository repository;

    @Autowired
    public StreamingServiceSourceController(StreamingServiceSourceContentStore contentStore, StreamingServiceSourceRepository repository) {this.contentStore = contentStore;
        this.repository = repository;
    }

    @PostMapping(value = "/streamingSource", consumes = {"multipart/form-data"})
    public ResponseEntity<?> addStreamingServiceSource(@RequestParam(name="name") String name, @RequestParam(name="logo")MultipartFile logo) throws IOException {

        StreamingServiceSource newSource = new StreamingServiceSource();
        newSource.setName(name);
        newSource.setMimeType(logo.getContentType());

        contentStore.setContent(newSource,logo.getInputStream());
        repository.save(newSource);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
