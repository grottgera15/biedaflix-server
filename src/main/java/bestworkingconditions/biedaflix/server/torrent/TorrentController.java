package bestworkingconditions.biedaflix.server.torrent;

import bestworkingconditions.biedaflix.server.torrent.model.TorrentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TorrentController {

    private final TorrentService torrentService;

    @Autowired
    public TorrentController(TorrentService torrentService) {this.torrentService = torrentService;}


    @GetMapping("/status")
    public ResponseEntity<List<TorrentInfo>> checkStatus(){
        List<TorrentInfo> info = torrentService.getTorrentsInfo();
        return ResponseEntity.ok(info);
    }
}
