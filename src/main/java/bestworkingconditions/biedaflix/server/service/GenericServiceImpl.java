package bestworkingconditions.biedaflix.server.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public abstract class GenericServiceImpl<T, R extends MongoRepository<T,String>> implements GenericService<T,R> {

    protected R repository;

    public GenericServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public T findById(String id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException()
        );
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
