package bestworkingconditions.biedaflix.server.vod.hdd;

import bestworkingconditions.biedaflix.server.vod.episode.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.vod.episode.model.Episode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HddController {

    
    private final EpisodeRepository episodeRepository;

    @Value("${biedaflix.hdd.max-size}")
    private double maxSize;

    @Autowired
    HddController(EpisodeRepository episodeRepository){
        this.episodeRepository = episodeRepository;
    }

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
