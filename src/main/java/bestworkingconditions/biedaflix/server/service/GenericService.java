package bestworkingconditions.biedaflix.server.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GenericService<T, R extends MongoRepository<?,?>> {
    T findById(String id);
    List<T> getAll();
    T create(T resource);
    T update(T resource);
    void deleteById(String id);
}