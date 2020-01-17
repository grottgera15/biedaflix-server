package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.StreamingServiceSource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamingServiceSourceRepository extends MongoRepository<StreamingServiceSource,String> {

}
