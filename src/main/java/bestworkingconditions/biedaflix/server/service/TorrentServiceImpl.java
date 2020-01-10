package bestworkingconditions.biedaflix.server.service;


import bestworkingconditions.biedaflix.server.model.TorrentInfo;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;
import bestworkingconditions.biedaflix.server.util.TorrentHttpEntityBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

@Service
public class TorrentServiceImpl implements TorrentService {

    @Override
    public void addTorrent(EpisodeRequest episodeRequest) {

        HttpEntity<MultiValueMap<String,String>> request = new TorrentHttpEntityBuilder()
                .addKeyValuePair("urls",episodeRequest.getMagnetLink())
                .addKeyValuePair("category","biedaflix")
                .addKeyValuePair("rename",episodeRequest.getName()).build();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/api/v2/torrents/add",request,String.class);

    }

    @Override
    public TorrentInfo getTorrentInfo(@NotBlank String name) {

        HttpEntity<MultiValueMap<String, String>> request = new TorrentHttpEntityBuilder().addKeyValuePair("filter","all")
                                                                                          .addKeyValuePair("category","biedaflix").build();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<TorrentInfo[]> responseEntity  = restTemplate.postForEntity("http://localhost:8080/api/v2/torrents/info",request,TorrentInfo[].class);
        return (Objects.requireNonNull(responseEntity.getBody()))[0];
    }

    @Override
    public void deleteTorrent(String torrentHash) {

    }
}
