package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.TorrentInfo;
import bestworkingconditions.biedaflix.server.service.TorrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
