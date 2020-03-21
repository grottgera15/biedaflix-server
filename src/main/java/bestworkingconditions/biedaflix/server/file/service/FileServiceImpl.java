package bestworkingconditions.biedaflix.server.file.service;

import bestworkingconditions.biedaflix.server.file.FileResource;
import bestworkingconditions.biedaflix.server.file.FileResourceContentStore;
import bestworkingconditions.biedaflix.server.file.FileResourceRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    private final FileResourceContentStore fileResourceContentStore;
    private final FileResourceRepository fileResourceRepository;

    @Autowired
    public FileServiceImpl(FileResourceContentStore fileResourceContentStore, FileResourceRepository fileResourceRepository) {
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

    @Override
    public FileResource saveFile(MultipartFile file){
        return setContent(createFileResource(file),file);
    }
}
