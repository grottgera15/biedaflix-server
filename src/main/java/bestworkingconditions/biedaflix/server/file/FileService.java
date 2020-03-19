package bestworkingconditions.biedaflix.server.file;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private final FileResourceContentStore fileResourceContentStore;
    private final FileResourceRepository fileResourceRepository;

    @Autowired
    public FileService(FileResourceContentStore fileResourceContentStore, FileResourceRepository fileResourceRepository) {
        this.fileResourceContentStore = fileResourceContentStore;
        this.fileResourceRepository = fileResourceRepository;
    }

    private FileResource createFileResource(MultipartFile file){
        FileResource fileResource = new FileResource();
        fileResource.setMimeType(file.getContentType());
        fileResource.setContentLength(file.getSize());
        return fileResourceRepository.save(fileResource);
    }

    @SneakyThrows
    private FileResource setContent(FileResource fileResource, MultipartFile file){
        fileResourceContentStore.setContent(fileResource,file.getInputStream());
        return fileResourceRepository.save(fileResource);
    }

    public FileResource saveFile(MultipartFile file){
        return setContent(createFileResource(file),file);
    }
}
