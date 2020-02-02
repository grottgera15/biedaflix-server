package bestworkingconditions.biedaflix.server.config;

import bestworkingconditions.biedaflix.server.properties.TorrentProperties;
import bestworkingconditions.biedaflix.server.repository.TorrentUriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;

@Configuration
public class TorrentConfig {

    private final TorrentProperties torrentProperties;
    private final TorrentUriRepository torrentUriRepository;

    @Autowired
    public TorrentConfig(TorrentProperties torrentProperties, TorrentUriRepository torrentUriRepository, RestTemplate restTemplate) {
        this.torrentProperties = torrentProperties;
        this.torrentUriRepository = torrentUriRepository;

        loginToTorrentApi(restTemplate);
    }

   public void loginToTorrentApi(RestTemplate restTemplate){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(torrentUriRepository.getAuthUri("login"))
                                                           .queryParam("username",torrentProperties.getUsername()).queryParam("password",torrentProperties.getPassword());


        ResponseEntity<String> response = restTemplate.getForEntity(builder.build().encode().toUri(),String.class);
   }
}
