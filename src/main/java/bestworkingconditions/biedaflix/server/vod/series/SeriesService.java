package bestworkingconditions.biedaflix.server.vod.series;

import bestworkingconditions.biedaflix.server.common.service.GenericServiceImpl;
import bestworkingconditions.biedaflix.server.file.service.FileService;
import bestworkingconditions.biedaflix.server.vod.series.model.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SeriesService extends GenericServiceImpl<Series,SeriesRepository> {

    private final FileService fileService;

    @Autowired
    public SeriesService(SeriesRepository repository, FileService fileService) {
        super(repository);
        this.fileService = fileService;
    }

    public Series AddLogo(String id,MultipartFile logo){
        Series series = findById(id);
        series.setLogo(fileService.saveFile(logo));
        return repository.save(series);
    }

}
