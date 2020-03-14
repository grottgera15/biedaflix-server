package bestworkingconditions.biedaflix.server.common.repository;

import bestworkingconditions.biedaflix.server.common.model.UserWatchProgress;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserWatchProgressRepository extends MongoRepository<UserWatchProgress,String> {
    Optional<UserWatchProgress> findByUserId(String userId);
}
