package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.CurrentlyDownloading;
import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.TorrentInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CurrentlyDownloadingRepository extends MongoRepository<CurrentlyDownloading,String> {

    List<CurrentlyDownloading> findAll();
    Optional<CurrentlyDownloading> findByTarget(Episode target);
    Optional<CurrentlyDownloading> findByTorrentInfo(TorrentInfo info);
    Optional<CurrentlyDownloading> findByTargetName(String name);
    Optional<CurrentlyDownloading> findByTorrentInfo_Hash (String hash);
}
