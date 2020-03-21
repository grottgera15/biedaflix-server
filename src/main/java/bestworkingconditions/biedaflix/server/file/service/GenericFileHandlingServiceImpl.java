package bestworkingconditions.biedaflix.server.file.service;

import bestworkingconditions.biedaflix.server.common.service.GenericServiceImpl;
import bestworkingconditions.biedaflix.server.file.FileResource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.BiConsumer;

public abstract class GenericFileHandlingServiceImpl<T,R extends MongoRepository<T, String>> extends GenericServiceImpl<T,R> implements GenericFileHandlingService<T,R>{

    private final FileService fileService;

    public GenericFileHandlingServiceImpl(R repository, FileService fileService) {
        super(repository);
        this.fileService = fileService;
    }

    @Override
    public T setFileReference(String id, MultipartFile file, BiConsumer<T, FileResource> setter) {
        T request = findById(id);
        setter.accept(request,fileService.saveFile(file));
        return repository.save(request);
    }
}
