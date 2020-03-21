package bestworkingconditions.biedaflix.server.file.service;

import bestworkingconditions.biedaflix.server.common.service.GenericService;
import bestworkingconditions.biedaflix.server.file.FileResource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.BiConsumer;

public interface GenericFileHandlingService<T,R extends MongoRepository<?, ?>> extends GenericService<T,R> {
    T setFileReference(String id, MultipartFile file, BiConsumer<T, FileResource> setter);
}
