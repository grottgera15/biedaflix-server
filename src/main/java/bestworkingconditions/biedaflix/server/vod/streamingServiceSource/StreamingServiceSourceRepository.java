package bestworkingconditions.biedaflix.server.vod.streamingServiceSource;

import bestworkingconditions.biedaflix.server.vod.streamingServiceSource.model.StreamingServiceSource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StreamingServiceSourceRepository extends MongoRepository<StreamingServiceSource,String> {
    Optional<StreamingServiceSource> findByName(String name);
}
