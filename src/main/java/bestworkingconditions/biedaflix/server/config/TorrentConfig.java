package bestworkingconditions.biedaflix.server.config;

import bestworkingconditions.biedaflix.server.properties.TorrentProperties;
import bestworkingconditions.biedaflix.server.repository.TorrentUriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class TorrentConfig {

    private final TorrentProperties torrentProperties;
    private final TorrentUriRepository torrentUriRepository;

    @Autowired
    public TorrentConfig(TorrentProperties torrentProperties, TorrentUriRepository torrentUriRepository) {this.torrentProperties = torrentProperties;
        this.torrentUriRepository = torrentUriRepository;
    }

    @Bean
    public String getLoginCookieValue(){
        String cookieUncut =  login();
        cookieUncut = cookieUncut.replace(';','_');
        String[] split = cookieUncut.split("_");
        return split[0];
    }

    private String login() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(torrentUriRepository.getAuthUri("login"))
                                                           .queryParam("username",torrentProperties.getUsername()).queryParam("password",torrentProperties.getPassword());


        ResponseEntity<String> response = new RestTemplate().getForEntity(builder.build().encode().toUri(),String.class);

        HttpHeaders headers = response.getHeaders();
        return headers.getFirst(HttpHeaders.SET_COOKIE);
    }


}
