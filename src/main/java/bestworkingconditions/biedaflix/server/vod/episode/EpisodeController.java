package bestworkingconditions.biedaflix.server.vod.episode;

import bestworkingconditions.biedaflix.server.vod.episode.model.Episode;
import bestworkingconditions.biedaflix.server.vod.episode.model.request.EpisodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;


@RestController
@RequestMapping(value = "/episodes")
public class EpisodeController {

    private final EpisodeService episodeService;
    private final EpisodeMapper mapper;

    @Autowired
    public EpisodeController(EpisodeService episodeService, EpisodeMapper mapper) {
        this.episodeService = episodeService;
        this.mapper = mapper;
    }


    @PostMapping(value = "/{id}/subtitles", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<Object> addSubtitles(@PathVariable String id,
                                               @NotNull @RequestParam  Locale language,
                                               @NotNull @RequestParam MultipartFile file ) {

        Episode episode = episodeService.setSubtitles(id,language,file);
        return new ResponseEntity<>(mapper.episodeLightResponseFromEpisode(episode),HttpStatus.CREATED);
    }

    @PostMapping(value = "", consumes = {"application/json"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> addEpisode(@Valid @RequestBody EpisodeRequest request) {

        Episode episode = episodeService.create(mapper.episodeFromEpisodeRequest(request));

        /*
        //FIXME add magnetLinkProcessing
        if(request.getMagnetLink().isPresent()){
            if(request.getMagnetLink().get().length() > 0) {
                torrentService.addTorrent(series.getName(),request.getMagnetLink().get(),savedEpisode);
            }
        }
        */

        return new ResponseEntity<>(mapper.episodeLightResponseFromEpisode(episode),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEpisodeInfo(@PathVariable String id) {
        return ResponseEntity.ok(mapper.episodeFullResponseFromEpisode(episodeService.findById(id)));
    }

    @PatchMapping(value = "/{id}", consumes = {"application/json"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> updateEpisode(
            @PathVariable String id,
            @RequestBody(required = false) EpisodeRequest request
            ){

        Episode episode = episodeService.fetchAndUpdate(id,request);

        //FIXME add magnetLinkProcessing
        /*
        if(request.getMagnetLink().isPresent()){
            if(request.getMagnetLink().get().length() > 0) {
                torrentService.addTorrent(series.getName(),request.getMagnetLink().get(),savedEpisode);
            }
        }
        */

        return ResponseEntity.ok(mapper.episodeLightResponseFromEpisode(episode));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> deleteEpisode(@PathVariable String id){
        episodeService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
