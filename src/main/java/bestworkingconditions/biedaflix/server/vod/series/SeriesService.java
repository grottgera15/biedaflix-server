package bestworkingconditions.biedaflix.server.vod.series;

import bestworkingconditions.biedaflix.server.file.service.FileService;
import bestworkingconditions.biedaflix.server.file.service.GenericFileHandlingServiceImpl;
import bestworkingconditions.biedaflix.server.vod.series.model.Series;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SeriesService extends GenericFileHandlingServiceImpl<Series,SeriesRepository> {

    @Autowired
    public SeriesService(SeriesRepository repository, FileService fileService) {
        super(repository, fileService);
    }

    public Series setLogo(String id, MultipartFile logo){
        return setFileReference(id,logo,Series::setLogo);
    }

    public Series setBanner(String id, MultipartFile logo){
        return setFileReference(id,logo,Series::setBanner);
    }

}
