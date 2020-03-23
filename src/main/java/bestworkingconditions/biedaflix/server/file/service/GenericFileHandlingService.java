package bestworkingconditions.biedaflix.server.file.service;

import bestworkingconditions.biedaflix.server.common.service.GenericService;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenericFileHandlingService<T,R extends MongoRepository<?, ?>> extends GenericService<T,R> {

}
