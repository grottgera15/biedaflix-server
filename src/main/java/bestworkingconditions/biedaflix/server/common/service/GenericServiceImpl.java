package bestworkingconditions.biedaflix.server.common.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
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
    public T create(@Valid T resource) {
        return repository.save(resource);
    }

    @Override
    public T update(@Valid T resource) {
        return repository.save(resource);
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
