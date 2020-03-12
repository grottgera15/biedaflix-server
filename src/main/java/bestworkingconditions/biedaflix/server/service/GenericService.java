package bestworkingconditions.biedaflix.server.service;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenericService<T, R extends MongoRepository<?,?>> {

    T findById(String id);
}
