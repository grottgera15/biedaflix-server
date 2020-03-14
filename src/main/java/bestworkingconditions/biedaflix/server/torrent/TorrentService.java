package bestworkingconditions.biedaflix.server.torrent;

import bestworkingconditions.biedaflix.server.torrent.model.TorrentCategory;
import bestworkingconditions.biedaflix.server.torrent.model.TorrentFileInfo;
import bestworkingconditions.biedaflix.server.torrent.model.TorrentInfo;
import bestworkingconditions.biedaflix.server.vod.episode.model.Episode;

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
