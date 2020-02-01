package bestworkingconditions.biedaflix.server.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;


public class TorrentHttpEntityBuilder {
    private MultiValueMap<String,String> map;
    private HttpHeaders headers;

    public TorrentHttpEntityBuilder(MediaType mediaType,String cookieValue) {
        map = new LinkedMultiValueMap<>();
        headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.set(HttpHeaders.COOKIE,"SID="+cookieValue);
    }

    public TorrentHttpEntityBuilder() {
        map = new LinkedMultiValueMap<>();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    }

    public TorrentHttpEntityBuilder addKeyValuePair(String key, String value){
        this.map.add(key,value);
        return this;
    }

    public HttpEntity<MultiValueMap<String,String>> build(){
        return new HttpEntity<>(this.map,this.headers);
    }
}
