package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.Series;
import bestworkingconditions.biedaflix.server.model.SeriesBanner;
import bestworkingconditions.biedaflix.server.model.SeriesLogo;
import bestworkingconditions.biedaflix.server.model.request.SeriesRequest;
import bestworkingconditions.biedaflix.server.repository.FileResourceContentStore;
import bestworkingconditions.biedaflix.server.repository.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class SeriesController {

    private final SeriesRepository repository;
    private final FileResourceContentStore fileResourceContentStore;

    @Autowired
    public SeriesController(SeriesRepository repository, FileResourceContentStore fileResourceContentStore) {
        this.repository = repository;
        this.fileResourceContentStore = fileResourceContentStore;
    }


    @GetMapping("/series")
    public ResponseEntity<List<Series>> GetAll() {
        List<Series> availableSeries = repository.findAll();
        return ResponseEntity.ok(availableSeries);
    }

    @PostMapping("/series")
    public ResponseEntity<Series> AddSeries(@Valid SeriesRequest request,
                                            @RequestParam(name = "banner", required = false) Optional<MultipartFile> banner,
                                            @RequestParam(name = "logo", required = false) Optional<MultipartFile> logo) throws IOException {

        Series newSeries = new Series();
        newSeries.setName(request.getName());
        newSeries.setDescription(request.getDescription());
        newSeries.setOnGoing(request.getOnGoing());
        newSeries.setStreamingServiceId(request.getSourceId());

        if(logo.isPresent()){
            SeriesLogo seriesLogo = new SeriesLogo(logo.get().getContentType(),newSeries.getName());
            newSeries.setLogo(seriesLogo);

            fileResourceContentStore.setContent(seriesLogo,logo.get().getInputStream());
        }

        if(banner.isPresent()){
            SeriesBanner seriesBanner = new SeriesBanner(banner.get().getContentType(),request.getName());
            newSeries.setBannerVideo(seriesBanner);

            fileResourceContentStore.setContent(seriesBanner,banner.get().getInputStream());
        }

        repository.save(newSeries);

        return ResponseEntity.ok(newSeries);
    }
}

