package bestworkingconditions.biedaflix.server.file.service;

import bestworkingconditions.biedaflix.server.file.FileResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileResource saveFile(MultipartFile file);
    void deleteFile(FileResource resource);
    void deleteFiles(Iterable<FileResource> resources);
}
