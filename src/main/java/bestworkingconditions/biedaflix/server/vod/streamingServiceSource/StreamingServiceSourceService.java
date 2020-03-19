package bestworkingconditions.biedaflix.server.vod.streamingServiceSource;

import bestworkingconditions.biedaflix.server.common.service.GenericServiceImpl;
import bestworkingconditions.biedaflix.server.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StreamingServiceSourceService extends GenericServiceImpl<StreamingServiceSource,StreamingServiceSourceRepository> {

    private final FileService fileService;

    @Autowired
    public StreamingServiceSourceService(StreamingServiceSourceRepository repository, FileService fileService) {
        super(repository);
        this.fileService = fileService;
    }

    public StreamingServiceSource setLogo(String id, MultipartFile file){
        StreamingServiceSource request = findById(id);
        request.setLogo(fileService.saveFile(file));
        return repository.save(request);
    }

    @Override
    public StreamingServiceSource create(StreamingServiceSource resource) {
        return null;
    }

    @Override
    public StreamingServiceSource update(StreamingServiceSource resource) {
        return null;
    }
}
