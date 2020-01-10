package bestworkingconditions.biedaflix.server.service;


import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TorrentServiceImpl implements TorrentService {

    @Override
    public void addTorrent(EpisodeRequest episodeRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("urls", episodeRequest.getMagnetLink());
        map.add("category","biedaflix");
        map.add("rename", episodeRequest.getName());

        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(map,headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/api/v2/torrents/add",request,String.class);

    }
}
