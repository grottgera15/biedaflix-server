package bestworkingconditions.biedaflix.server.common.repository;

import bestworkingconditions.biedaflix.server.common.model.StreamingServiceSource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StreamingServiceSourceRepository extends MongoRepository<StreamingServiceSource,String> {
    Optional<StreamingServiceSource> findByName(String name);
}
