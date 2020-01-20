package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.Series;
import bestworkingconditions.biedaflix.server.model.SeriesBanner;
import bestworkingconditions.biedaflix.server.model.SeriesLogo;
import bestworkingconditions.biedaflix.server.model.request.SeriesRequest;
import bestworkingconditions.biedaflix.server.model.response.MediaFilesResponse;
import bestworkingconditions.biedaflix.server.model.response.SeriesResponse;
import bestworkingconditions.biedaflix.server.repository.FileResourceContentStore;
import bestworkingconditions.biedaflix.server.repository.SeriesRepository;
import com.sun.jndi.toolkit.url.Uri;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
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

        List<SeriesResponse> response = new ArrayList<>();

        for(Series s : availableSeries){
            SeriesResponse r = new SeriesResponse(s.getId(),s.getName(),s.getDescription(),
                    new MediaFilesResponse())



        }




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
            SeriesLogo seriesLogo = new SeriesLogo(FilenameUtils.getExtension(logo.get().getOriginalFilename()),newSeries.getFolderName());
            newSeries.setLogo(seriesLogo);


            fileResourceContentStore.setContent(seriesLogo,logo.get().getInputStream());
        }

        if(banner.isPresent()){
            SeriesBanner seriesBanner = new SeriesBanner(FilenameUtils.getExtension(banner.get().getOriginalFilename()),request.getName());
            newSeries.setBannerVideo(seriesBanner);

            fileResourceContentStore.setContent(seriesBanner,banner.get().getInputStream());
        }

        repository.save(newSeries);

        return ResponseEntity.ok(newSeries);
    }
}

