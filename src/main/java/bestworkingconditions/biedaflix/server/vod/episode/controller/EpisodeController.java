package bestworkingconditions.biedaflix.server.vod.episode.controller;

import bestworkingconditions.biedaflix.server.file.FileResourceContentStore;
import bestworkingconditions.biedaflix.server.vod.episode.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.vod.episode.service.EpisodeService;
import bestworkingconditions.biedaflix.server.vod.episode.model.EpisodeSubtitles;
import bestworkingconditions.biedaflix.server.vod.episode.model.Episode;
import bestworkingconditions.biedaflix.server.vod.episode.model.response.EpisodeFullResponse;
import bestworkingconditions.biedaflix.server.vod.episode.model.response.EpisodeLightResponse;
import bestworkingconditions.biedaflix.server.vod.episode.model.request.EpisodeRequest;
import bestworkingconditions.biedaflix.server.vod.series.model.Series;
import bestworkingconditions.biedaflix.server.vod.series.SeriesRepository;
import bestworkingconditions.biedaflix.server.torrent.TorrentService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.*;


@RestController
public class EpisodeController {

    private final EpisodeRepository episodeRepository;
    private final SeriesRepository seriesRepository;
    private final FileResourceContentStore fileResourceContentStore;
    private final TorrentService torrentService;
    private final EpisodeService episodeService;

    @Autowired
    public EpisodeController(EpisodeRepository episodeRepository, SeriesRepository seriesRepository, FileResourceContentStore fileResourceContentStore, FileResourceContentStore fileResourceContentStore1, TorrentService torrentService, EpisodeService episodeService) {
        this.episodeRepository = episodeRepository;
        this.seriesRepository = seriesRepository;
        this.fileResourceContentStore = fileResourceContentStore1;
        //this.fileResourceContentStore = fileResourceContentStore;
        this.torrentService = torrentService;
        this.episodeService = episodeService;
    }

    @PostMapping(value = "/episodes/{id}/subtitles", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<Object> addSubtitles(@PathVariable String id,
                                               @NotNull @RequestParam EpisodeSubtitles.SubtitlesLanguage language,
                                               @NotNull @RequestParam MultipartFile file ) throws IOException {

        Optional<Episode> optionalEpisode = episodeRepository.findById(id);
        Episode episode = optionalEpisode.orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Episode of given id does not exist!"));

        Series episodeSeries = seriesRepository.findById(episode.getSeriesId()).get();

        EpisodeSubtitles subs = new EpisodeSubtitles(FilenameUtils.getExtension(file.getOriginalFilename()),episodeSeries.getFolderName(),episode.getId(),language);
        fileResourceContentStore.setContent(subs,file.getInputStream());

        episode.getEpisodeSubtitles().add(subs);

        episodeRepository.save(episode);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/episodes/{id}")
    public ResponseEntity<EpisodeFullResponse> getEpisodeInfo(@PathVariable String id) {

        Episode ep = episodeRepository.findById(id)
                                      .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Episode of given id does not exist!")
                                      );

        return ResponseEntity.ok(episodeService.episodeFullResponseFromEpisode(ep));
    }

    @PostMapping(value = "/episodes", consumes = {"application/json"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> addEpisode(@Valid @RequestBody EpisodeRequest request) {

        Series series = seriesRepository.findById(request.getSeriesId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Series of given id does not exist!"));


        if(episodeRepository.existsEpisodeByEpisodeNumberAndSeasonNumber(request.getEpisodeNumber(),request.getSeasonNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Episode of given number already exists");
        }

        Episode episode = episodeService.episodeFromEpisodeRequest(request);
        Episode savedEpisode = episodeRepository.save(episode);

        if(request.getMagnetLink().isPresent()){
            if(request.getMagnetLink().get().length() > 0) {
                torrentService.addTorrent(series.getName(),request.getMagnetLink().get(),savedEpisode);
            }
        }

        return new ResponseEntity<>(new EpisodeLightResponse(savedEpisode),HttpStatus.CREATED);
    }

    @PatchMapping(value = "/episodes/{id}", consumes = {"application/json"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> updateEpisode(
            @PathVariable String id,
            @RequestBody(required = false) EpisodePatchRequest request
            ){

        Episode requestEpisode = episodeRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Episode of given id does not exist!")
        );

        request.getSeriesId().ifPresent(requestEpisode::setSeriesId);
        request.getEpisodeNumber().ifPresent(requestEpisode::setEpisodeNumber);
        request.getName().ifPresent(requestEpisode::setName);
        request.getReleaseDate().ifPresent(requestEpisode::setReleaseDate);
        request.getSeasonNumber().ifPresent(requestEpisode::setSeasonNumber);


        Series series = seriesRepository.findById(requestEpisode.getSeriesId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Series of given id does not exist!")
        );

        Optional<Episode> match = episodeRepository.findByEpisodeNumberAndSeasonNumber(requestEpisode.getEpisodeNumber(),requestEpisode.getSeasonNumber());

        if(match.isPresent() && !match.get().getId().equals(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Episode of given number already exists");
        }

        Episode savedEpisode = episodeRepository.save(requestEpisode);

        if(request.getMagnetLink().isPresent()){
            if(request.getMagnetLink().get().length() > 0) {
                torrentService.addTorrent(series.getName(),request.getMagnetLink().get(),savedEpisode);
            }

        }

        return ResponseEntity.ok(episodeService.episodeFullResponseFromEpisode(savedEpisode));
    }

    @DeleteMapping("/episodes/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_SERIES')")
    public ResponseEntity<?> deleteEpisode(@PathVariable String id){
        episodeService.deleteEpisode(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
