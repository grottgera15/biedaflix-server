package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.Season;
import bestworkingconditions.biedaflix.server.model.Series;
import bestworkingconditions.biedaflix.server.model.TorrentInfo;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;
import bestworkingconditions.biedaflix.server.repository.SeriesRepository;
import bestworkingconditions.biedaflix.server.service.TorrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EpisodeController {

    private final SeriesRepository repository;
    private final TorrentService torrentService;

    @Autowired
    public EpisodeController(SeriesRepository repository, TorrentService torrentService) {
        this.repository = repository;
        this.torrentService = torrentService;
    }

    private Season getRequestedSeasonOrCreate(Series series, int seasonNumber){

        for (Season s : series.getSeasons()){
            if(s.getSeasonNumber() == seasonNumber)
                return s;
        }

        Season newSeason = new Season(seasonNumber);
        series.getSeasons().add(newSeason);
        return newSeason;
    }

    @PostMapping("/episode")
    public ResponseEntity<List<TorrentInfo>> AddEpisode(@Valid @RequestBody EpisodeRequest request) {
        Series series = repository.findById(request.getSeriesId())
                                                   .orElseThrow(() ->
                                                           new ResponseStatusException(HttpStatus.NOT_FOUND, "Series not found!"));

        Episode newEpisode = new Episode(request.getEpisodeNumber(),request.getName(),request.getReleaseDate());

        Season currentSeason = getRequestedSeasonOrCreate(series,request.getSeasonNumber());

        if(currentSeason.getEpisodes().stream().anyMatch(t->t.getName().equals(request.getName())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Episode of given name already exists in database");

        if(currentSeason.getEpisodes().stream().noneMatch(t -> t.getEpisodeNumber() == newEpisode.getEpisodeNumber())) {
            currentSeason.getEpisodes().add(newEpisode);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Episode of given number already exists in database!");
        }

        repository.save(series);

        torrentService.addTorrent(request);

        List<TorrentInfo> info = torrentService.getTorrentsInfo();

        return ResponseEntity.ok(info);
    }

    @GetMapping("/status")
    public ResponseEntity<List<TorrentInfo>> checkStatus(){
        List<TorrentInfo> info = torrentService.getTorrentsInfo();
        return ResponseEntity.ok(info);
    }
}
