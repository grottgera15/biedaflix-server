package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.CurrentlyDownloading;
import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.TorrentInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CurrentlyDownloadingRepository extends MongoRepository<CurrentlyDownloading,String> {

    List<CurrentlyDownloading> findAll();
    List<CurrentlyDownloading> findByTarget(Episode target);
    List<CurrentlyDownloading> findByTorrentInfo(TorrentInfo info);
}
