package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.Series;
import bestworkingconditions.biedaflix.server.repository.FileContentStore;
import bestworkingconditions.biedaflix.server.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SeriesController {

    private final SeriesRepository repository;
    private final FileContentStore fileContentStore;


    @Autowired
    public SeriesController(SeriesRepository repository, FileContentStore fileContentStore) {this.repository = repository;
        this.fileContentStore = fileContentStore;
    }

    @GetMapping("/series")
    public ResponseEntity<List<Series>> GetAll() {
        List<Series> availableSeries = repository.findAll();
        return ResponseEntity.ok(availableSeries);
    }


    @PostMapping("/series")
    public ResponseEntity<Series> AddSeries(@Valid @RequestBody Series series) {
        Series saved = repository.save(series);
        return ResponseEntity.ok(saved);
    }
}

