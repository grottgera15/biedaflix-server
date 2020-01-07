package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EpisodeController {

    public ResponseEntity<Episode> AddEpisode(@Valid @RequestBody EpisodeRequest request){
        String seriesID = request.get
    }

}
