package bestworkingconditions.biedaflix.server.service;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.TorrentInfo;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;

public interface TorrentService {
    public void addTorrent(EpisodeRequest request);
    public TorrentInfo getTorrentInfo(String name);
}
