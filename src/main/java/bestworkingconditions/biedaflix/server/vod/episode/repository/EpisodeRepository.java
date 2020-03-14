package bestworkingconditions.biedaflix.server.vod.episode.repository;

import bestworkingconditions.biedaflix.server.vod.episode.model.Episode;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends MongoRepository<Episode,String> {
    List<Episode> findAllBySeriesId(String seriesId);
    List<Episode> findAllBySeriesIdOrderByEpisodeNumber(String seriesId);
    Boolean existsEpisodeByEpisodeNumberAndSeasonNumber (int episodeNumber, int seasinNumber);
    Optional<Episode> findByEpisodeNumberAndSeasonNumber(int episodeNumber, int seasonNumber);
    Optional<Episode> findBySeriesIdAndSeasonNumberAndEpisodeNumber(String seriesId,int seasonNumber,int episodeNumber);
    Optional<Episode> findById(String episodeId);
}
