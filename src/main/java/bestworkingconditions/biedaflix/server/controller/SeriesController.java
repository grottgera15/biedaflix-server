package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.Series;
import bestworkingconditions.biedaflix.server.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class SeriesController {

    private  final SeriesRepository repository;

    @Autowired
    public SeriesController(SeriesRepository repository) {this.repository = repository;}

    @PostMapping("/series")
    public ResponseEntity<Series> AddSeries(@Valid @RequestBody Series series){
        Series saved = repository.save(series);
        return ResponseEntity.ok(saved);
    }

}
