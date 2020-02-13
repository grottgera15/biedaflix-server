package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.*;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;
import bestworkingconditions.biedaflix.server.model.response.EpisodeFullResponse;
import bestworkingconditions.biedaflix.server.model.response.MediaFilesResponse;
import bestworkingconditions.biedaflix.server.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.repository.FileResourceContentStore;
import bestworkingconditions.biedaflix.server.repository.SeriesRepository;
import bestworkingconditions.biedaflix.server.service.SeriesService;
import bestworkingconditions.biedaflix.server.service.TorrentService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

@RestController
public class EpisodeController {

    private final EpisodeRepository episodeRepository;
    private final SeriesRepository seriesRepository;
    private final FileResourceContentStore fileResourceContentStore;
    private final TorrentService torrentService;
    private final SeriesService seriesService;

    @Autowired
    public EpisodeController(EpisodeRepository episodeRepository, SeriesRepository seriesRepository, FileResourceContentStore fileResourceContentStore, TorrentService torrentService, SeriesService seriesService) {
        this.episodeRepository = episodeRepository;
        this.seriesRepository = seriesRepository;
        this.fileResourceContentStore = fileResourceContentStore;
        this.torrentService = torrentService;
        this.seriesService = seriesService;
    }


    @PostMapping("/addSubtitles")
    public ResponseEntity<Object> AddSubtitles(@NotBlank @RequestParam  String episodeId,
                                                @NotNull @RequestParam EpisodeSubtitles.SubtitlesLanguage language,
                                                @NotNull @RequestParam MultipartFile file) throws IOException {

        Optional<Episode> optionalEpisode = episodeRepository.findById(episodeId);
        Episode episode = optionalEpisode.orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Episode of given id does not exist!"));

        Series episodeSeries = seriesRepository.findById(episode.getSeriesId()).get();

        EpisodeSubtitles subs = new EpisodeSubtitles(FilenameUtils.getExtension(file.getOriginalFilename()),episodeSeries.getFolderName(),episode.getSeasonNumber(),episode.getEpisodeNumber(),language);
        fileResourceContentStore.setContent(subs,file.getInputStream());

        episode.getEpisodeSubtitles().add(subs);
        episodeRepository.save(episode);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/episode")
    public ResponseEntity<EpisodeFullResponse> getEpisodeInfo(@NotBlank @RequestParam String id) throws MalformedURLException {

        Episode ep = episodeRepository.findById(id)
                                      .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Episode of given id does not exist!"));

        Map<String, String> videoSources = new HashMap<>();
        for (EpisodeVideo episodeVideo : ep.getVideoFiles()) {
            videoSources.put(episodeVideo.getVideoQuality()
                                         .getQuality(), seriesService.getSeriesResourceURL(episodeVideo.getFilePath())
                                                                     .toString());
        }

        Map<String, String> subtitles = new HashMap<>();
        for (EpisodeSubtitles episodeSubtitles : ep.getEpisodeSubtitles()) {
            subtitles.put(episodeSubtitles.getLanguage()
                                          .getValue(), seriesService.getSeriesResourceURL(episodeSubtitles.getFilePath())
                                                                    .toString());
        }

        List<MediaFilesResponse> thumbs = new ArrayList<>();
        for (EpisodeThumbs episodeThumbs : ep.getEpisodeThumbs()) {
            thumbs.add(new MediaFilesResponse(seriesService.getSeriesResourceURL(episodeThumbs.getFilePath())));
        }

        thumbs.sort( ( a, b) ->  a.getPath().toString().compareTo(b.getPath().toString()));

        EpisodeFullResponse response = new EpisodeFullResponse(
                ep.getId(),
                ep.getEpisodeNumber(),
                ep.getName(),
                ep.isAvailable(),
                ep.getReleaseDate(),
                videoSources,
                subtitles,
                thumbs
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/episode")
    public ResponseEntity<List<TorrentInfo>> AddEpisode(@Valid @RequestBody EpisodeRequest request) {

        Series series = seriesRepository.findById(request.getSeriesId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Series of given id does not exist!"));

        Episode episode = new Episode(request.getSeriesId(),
                request.getSeasonNumber(),
                request.getEpisodeNumber(),
                request.getName(),
                request.getReleaseDate());

        torrentService.addTorrent(series.getName(),request,episode);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
