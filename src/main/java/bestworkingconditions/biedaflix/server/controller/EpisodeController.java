package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.Season;
import bestworkingconditions.biedaflix.server.model.Series;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;
import bestworkingconditions.biedaflix.server.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class EpisodeController {

    private final SeriesRepository repository;

    @Autowired
    public EpisodeController(SeriesRepository repository) {this.repository = repository;}

    @PostMapping("/episode")
    public ResponseEntity<Series> AddEpisode(@Valid @RequestBody EpisodeRequest request) {
        Series series = repository.findById(request.getSeriesId())
                                                   .orElseThrow(() ->
                                                           new ResponseStatusException(HttpStatus.NOT_FOUND, "Series not found!"));

        Episode newEpisode = new Episode(request.getEpisodeNumber(),request.getName(),request.getReleaseDate());

        Season currentSeason = null;
        if(series.getSeasons().stream().noneMatch(t -> t.getSeasonNumber() == request.getSeasonNumber())){
            currentSeason = new Season(request.getSeasonNumber());
            series.getSeasons().add(currentSeason);
        }else {
            currentSeason = series.getSeasons().get(request.getSeasonNumber() - 1);
        }

        if(currentSeason.getEpisodes().stream().noneMatch(t -> t.getEpisodeNumber() == newEpisode.getEpisodeNumber())) {
            currentSeason.getEpisodes().add(newEpisode);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Episode of given number already exists in database!");
        }

        repository.save(series);
        return ResponseEntity.ok(series);
    }
}
