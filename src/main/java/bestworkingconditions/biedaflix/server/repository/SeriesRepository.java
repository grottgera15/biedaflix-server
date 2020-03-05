package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.Series;
import bestworkingconditions.biedaflix.server.model.SeriesStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends MongoRepository<Series, String> {
    boolean existsById(String id);
    List<Series> findAll();
    Optional<Series> findById(String id);
    List<Series> findAllByStreamingServiceId(String id);
}
