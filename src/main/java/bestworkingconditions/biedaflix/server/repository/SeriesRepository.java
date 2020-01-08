package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.Series;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends MongoRepository<Series, String> {
    List<Series> findAll();
    Optional<Series> findById(String id);
}
