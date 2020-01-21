package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.EpisodeSubtitles;
import bestworkingconditions.biedaflix.server.model.Series;
import bestworkingconditions.biedaflix.server.model.TorrentInfo;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;
import bestworkingconditions.biedaflix.server.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.repository.FileResourceContentStore;
import bestworkingconditions.biedaflix.server.repository.SeriesRepository;
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
import java.util.List;
import java.util.Optional;

@RestController
public class EpisodeController {

    private final EpisodeRepository episodeRepository;
    private final SeriesRepository seriesRepository;
    private final FileResourceContentStore fileResourceContentStore;
    private final TorrentService torrentService;

    @Autowired
    public EpisodeController(EpisodeRepository episodeRepository, SeriesRepository seriesRepository, FileResourceContentStore fileResourceContentStore, TorrentService torrentService) {
        this.episodeRepository = episodeRepository;
        this.seriesRepository = seriesRepository;
        this.fileResourceContentStore = fileResourceContentStore;
        this.torrentService = torrentService;
    }


    @PostMapping("/addSubtitles")
    public ResponseEntity<Object> AddSubtitles(@NotBlank String episodeId,
                                                @NotNull Episode.SubtitlesLanguage language,
                                                @NotNull @RequestParam MultipartFile file) throws IOException {

        Optional<Episode> optionalEpisode = episodeRepository.findById(episodeId);
        Episode episode = optionalEpisode.orElseThrow( () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Episode of given id does not exist!"));

        Series episodeSeries = seriesRepository.findById(episode.getSeriesId()).get();

        EpisodeSubtitles subs = new EpisodeSubtitles(file.getContentType(),episodeSeries.getFolderName(),episode.getSeasonNumber(),episode.getEpisodeNumber(),language);
        fileResourceContentStore.setContent(subs,file.getInputStream());

        episode.getEpisodeSubtitles().add(new EpisodeSubtitles(FilenameUtils.getExtension(file.getOriginalFilename()),
                episodeSeries.getFolderName(),episode.getSeasonNumber(),episode.getEpisodeNumber(),language));

            return new ResponseEntity<>(HttpStatus.CREATED);

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
