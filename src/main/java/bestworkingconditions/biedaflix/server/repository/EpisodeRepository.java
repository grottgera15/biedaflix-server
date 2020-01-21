package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.Episode;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EpisodeRepository extends MongoRepository<Episode,String> {
}
