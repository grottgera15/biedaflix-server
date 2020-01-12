package bestworkingconditions.biedaflix.server.service;


import bestworkingconditions.biedaflix.server.model.TorrentInfo;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;
import bestworkingconditions.biedaflix.server.repository.TorrentUriRepository;
import bestworkingconditions.biedaflix.server.util.TorrentHttpEntityBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TorrentServiceImpl implements TorrentService {

    private final TorrentUriRepository torrentUriRepository;

    @Autowired
    public TorrentServiceImpl(TorrentUriRepository torrentUriRepository) {this.torrentUriRepository = torrentUriRepository;}

    @Override
    public void addTorrent(EpisodeRequest episodeRequest) {

        HttpEntity<MultiValueMap<String,String>> request = new TorrentHttpEntityBuilder()
                .addKeyValuePair("urls",episodeRequest.getMagnetLink())
                .addKeyValuePair("category","biedaflix")
                .addKeyValuePair("rename",episodeRequest.getName()).build();

        ResponseEntity<String> response = new RestTemplate().postForEntity(torrentUriRepository.getUri("add"),request,String.class);

    }

    @Override
    public List<TorrentInfo> getTorrentsInfo(@NotBlank String name) {

        HttpEntity<MultiValueMap<String, String>> request = new TorrentHttpEntityBuilder().addKeyValuePair("filter","all")
                                                                                          .addKeyValuePair("category","biedaflix").build();
        ResponseEntity<TorrentInfo[]> responseEntity  = new RestTemplate().getForEntity(torrentUriRepository.getUri("info"),TorrentInfo[].class,request);

        if(responseEntity.getBody() != null)
            return new ArrayList<>(Arrays.asList(responseEntity.getBody()));
        else
            return new ArrayList<>();
    }

    @Override
    public void deleteTorrent(String torrentHash,boolean deleteFiles) {
        HttpEntity<MultiValueMap<String, String>> request = new TorrentHttpEntityBuilder().addKeyValuePair("hashes", torrentHash)
                                                                                          .addKeyValuePair("deleteFiles", Boolean.toString(deleteFiles))
                                                                                          .build();

        ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(torrentUriRepository.getUri("delete"),request, String.class);
    }
}
