package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.UserWatchProgress;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserWatchProgressRepository extends MongoRepository<UserWatchProgress,String> {
    Optional<UserWatchProgress> findByUserId(String userId);
}
