package bestworkingconditions.biedaflix.server.common.service;

import bestworkingconditions.biedaflix.server.episode.Episode;
import bestworkingconditions.biedaflix.server.common.model.TorrentCategory;
import bestworkingconditions.biedaflix.server.common.model.TorrentFileInfo;
import bestworkingconditions.biedaflix.server.common.model.TorrentInfo;

import java.util.List;

public interface TorrentService {
    void addTorrent(String seriesName, String mangetLink, Episode episode);
    List<TorrentInfo> getTorrentsInfo();
    void deleteTorrent(String name, boolean deleteFiles);
    void pauseTorrents(List<String> torrentHashes);
    void resumeTorrents(List<String> torrentHashes);
    void setTorrentCategory(List<String> torrentHashes, TorrentCategory category);
    List<TorrentFileInfo> getFilesInfo(String torrentHash);
}
