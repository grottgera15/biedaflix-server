package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.Episode;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends MongoRepository<Episode,String> {
    List<Episode> findAllBySeriesId(String seriesId);
    List<Episode> findAllBySeriesIdOrderByEpisodeNumber(String seriesId);
    Optional<Episode> findById(String episodeId);
}
