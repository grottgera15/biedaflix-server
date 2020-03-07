package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.Episode;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends MongoRepository<Episode,String> {
    List<Episode> findAllBySeriesId(String seriesId);
    List<Episode> findAllBySeriesIdOrderByEpisodeNumber(String seriesId);
    Boolean existsEpisodeByEpisodeNumberAndSeasonNumber (int episodeNumber, int seasinNumber);
    Optional<Episode> findByEpisodeNumberAndSeasonNumber(int episodeNumber, int seasonNumber);
    Optional<Episode> findById(String episodeId);
}
