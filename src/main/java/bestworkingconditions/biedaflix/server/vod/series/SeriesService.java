package bestworkingconditions.biedaflix.server.vod.series;

import bestworkingconditions.biedaflix.server.file.service.FileService;
import bestworkingconditions.biedaflix.server.file.service.GenericFileHandlingServiceImpl;
import bestworkingconditions.biedaflix.server.vod.series.model.Series;
import bestworkingconditions.biedaflix.server.vod.series.model.SeriesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SeriesService extends GenericFileHandlingServiceImpl<Series,SeriesRepository> {

    private final SeriesMapper mapper;

    @Autowired
    public SeriesService(SeriesRepository repository, FileService fileService, SeriesMapper mapper) {
        super(repository, fileService);
        this.mapper = mapper;
    }

    public Series setLogo(String id, MultipartFile logo){
        return setFileReference(id,logo,Series::setLogo);
    }

    public Series setBanner(String id, MultipartFile logo){
        return setFileReference(id,logo,Series::setBanner);
    }

    public Series fetchAndUpdateSeries(String id, SeriesRequest request){
        Series s = findById(id);
        return repository.save(mapper.updateSeriesFromSeriesRequest(request,s));
    }

}
