package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.Series;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SeriesRepository extends MongoRepository<Series, String> {
    List<Series> findAll();
}
