package bestworkingconditions.biedaflix.server.service;

import bestworkingconditions.biedaflix.server.model.TorrentInfo;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;

import java.util.List;

public interface TorrentService {
    void addTorrent(EpisodeRequest request);
    List<TorrentInfo> getTorrentsInfo(String name);
    void deleteTorrent(String name, boolean deleteFiles);

}
