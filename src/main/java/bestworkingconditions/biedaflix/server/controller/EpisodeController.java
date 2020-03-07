package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.*;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;
import bestworkingconditions.biedaflix.server.model.response.EpisodeFullResponse;
import bestworkingconditions.biedaflix.server.model.response.MediaFilesResponse;
import bestworkingconditions.biedaflix.server.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.repository.FileResourceContentStore;
import bestworkingconditions.biedaflix.server.repository.SeriesRepository;
import bestworkingconditions.biedaflix.server.service.EpisodeService;
import bestworkingconditions.biedaflix.server.service.SeriesService;
import bestworkingconditions.biedaflix.server.service.TorrentService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.*;

@RestController
public class EpisodeController {

    private final EpisodeRepository episodeRepository;
    private final SeriesRepository seriesRepository;
    private final FileResourceContentStore fileResourceContentStore;
    private final TorrentService torrentService;
    private final SeriesService seriesService;
    private final EpisodeService episodeService;

    @Autowired
    public EpisodeController(EpisodeRepository episodeRepository, SeriesRepository seriesRepository, FileResourceContentStore fileResourceContentStore, TorrentService torrentService, SeriesService seriesService, EpisodeService episodeService) {
        this.episodeRepository = episodeRepository;
        this.seriesRepository = seriesRepository;
        this.fileResourceContentStore = fileResourceContentStore;
        this.torrentService = torrentService;
        this.seriesService = seriesService;
        this.episodeService = episodeService;
    }

    @PostMapping(name = "/addSubtitles", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<Object> addSubtitles(@NotBlank @RequestParam  String episodeId,
                                                @NotNull @RequestParam EpisodeSubtitles.SubtitlesLanguage language,
                                                @NotNull @RequestParam MultipartFile file) throws IOException {

        Optional<Episode> optionalEpisode = episodeRepository.findById(episodeId);
        Episode episode = optionalEpisode.orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Episode of given id does not exist!"));

        Series episodeSeries = seriesRepository.findById(episode.getSeriesId()).get();

        EpisodeSubtitles subs = new EpisodeSubtitles(FilenameUtils.getExtension(file.getOriginalFilename()),episodeSeries.getFolderName(),episode.getId(),language);
        fileResourceContentStore.setContent(subs,file.getInputStream());

        episode.getEpisodeSubtitles().add(subs);

        episodeRepository.save(episode);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/episode")
    public ResponseEntity<EpisodeFullResponse> getEpisodeInfo(@NotBlank @RequestParam String id) {

        Episode ep = episodeRepository.findById(id)
                                      .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Episode of given id does not exist!")
                                      );

        return ResponseEntity.ok(episodeService.episodeFullResponseFromEpisode(ep));
    }

    @PostMapping("/episode")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> addEpisode(@Valid @RequestBody EpisodeRequest request) {

        Series series = seriesRepository.findById(request.getSeriesId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Series of given id does not exist!"));




        if(episodeRepository.existsEpisodeByEpisodeNumberAndSeasonNumber(request.getEpisodeNumber(),request.getSeasonNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Episode of given number already exists");
        }

        Episode episode = episodeService.episodeFromEpisodeRequest(request);
        episodeRepository.save(episode);

        request.getMagnetLink().ifPresent(x -> torrentService.addTorrent(series.getName(),request,episode));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/episode")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> updateEpisode(
            @RequestParam String id,
            @Valid @RequestBody(required = false) EpisodeRequest request
            ){

        if(!episodeRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Episode of given id does not exist!");
        }

        Series series = seriesRepository.findById(request.getSeriesId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Series of given id does not exist!")
        );

        Optional<Episode> match = episodeRepository.findByEpisodeNumberAndSeasonNumber(request.getEpisodeNumber(),request.getSeasonNumber());

        if(match.isPresent() && !match.get().getId().equals(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Episode of given number already exists");
        }

        Episode episode = episodeService.episodeFromEpisodeRequest(request);
        episodeRepository.save(episode);

        request.getMagnetLink().ifPresent(x -> torrentService.addTorrent(series.getName(),request,episode));

        return ResponseEntity.ok(episodeRepository.save(episodeService.episodeFromEpisodeRequest(request)));
    }

    @DeleteMapping("/episode")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> deleteEpisode(@RequestParam String id){
        episodeRepository.deleteById(id);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
