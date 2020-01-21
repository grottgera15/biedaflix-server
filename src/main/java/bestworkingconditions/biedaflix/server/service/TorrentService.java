package bestworkingconditions.biedaflix.server.service;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.TorrentInfo;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;

import java.util.List;

public interface TorrentService {
    void addTorrent(String seriesName, EpisodeRequest request, Episode episode);
    List<TorrentInfo> getTorrentsInfo();
    void deleteTorrent(String name, boolean deleteFiles);
    void pauseTorrents(List<String> torrentHashes);
    void resumeTorrents(List<String> torrentHashes);

}
