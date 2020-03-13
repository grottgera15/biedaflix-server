package bestworkingconditions.biedaflix.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.response.HddResponse;
import bestworkingconditions.biedaflix.server.repository.EpisodeRepository;

@RestController
public class HddController {

    @Autowired
    private EpisodeRepository episodeRepository;
    @Value("${biedaflix.hdd.max-size}")
    private double maxSize;

    public double getAllEpisodesSize(){
        double size = 0;
        for(Episode episode : episodeRepository.findAll()){
            size += episode.getSize();
        }
        return size;
    }

    @GetMapping("/hdd")
    ResponseEntity<HddResponse> hdd() {
        return ResponseEntity.ok(new HddResponse(getAllEpisodesSize(), maxSize));
    }

}
