package bestworkingconditions.biedaflix.server.common.repository;

import bestworkingconditions.biedaflix.server.common.model.CurrentlyDownloading;
import bestworkingconditions.biedaflix.server.episode.model.Episode;
import bestworkingconditions.biedaflix.server.common.model.TorrentInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CurrentlyDownloadingRepository extends MongoRepository<CurrentlyDownloading,String> {

    List<CurrentlyDownloading> findAll();
    Optional<CurrentlyDownloading> findByTarget(Episode target);
    Optional<CurrentlyDownloading> findByTorrentInfo(TorrentInfo info);
    Optional<CurrentlyDownloading> findByTargetName(String name);
    Optional<CurrentlyDownloading> findByTorrentInfo_Hash (String hash);
}
