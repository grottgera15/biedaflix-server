package bestworkingconditions.biedaflix.server.vod.streamingServiceSource;

import bestworkingconditions.biedaflix.server.file.service.FileService;
import bestworkingconditions.biedaflix.server.file.service.GenericFileHandlingServiceImpl;
import bestworkingconditions.biedaflix.server.vod.streamingServiceSource.model.StreamingServiceSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StreamingServiceSourceService extends GenericFileHandlingServiceImpl<StreamingServiceSource,StreamingServiceSourceRepository> {

    @Autowired
    public StreamingServiceSourceService(StreamingServiceSourceRepository repository, FileService fileService) {
        super(repository, fileService);
    }

    public StreamingServiceSource setLogo(String id, MultipartFile file){
        return setFileReference(id,file,StreamingServiceSource::setLogo);
    }
}
