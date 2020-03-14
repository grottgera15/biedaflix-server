package bestworkingconditions.biedaflix.server.identity.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserWatchProgressRepository extends MongoRepository<UserWatchProgress,String> {
    Optional<UserWatchProgress> findByUserId(String userId);
}
