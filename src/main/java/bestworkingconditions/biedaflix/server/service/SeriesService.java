package bestworkingconditions.biedaflix.server.service;

import bestworkingconditions.biedaflix.server.properties.AppProperties;
import bestworkingconditions.biedaflix.server.properties.StoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class SeriesService {

    private final AppProperties appProperties;
    private final StoreProperties storeProperties;

    @Autowired
    public SeriesService(AppProperties appProperties, StoreProperties storeProperties) {this.appProperties = appProperties;
        this.storeProperties = storeProperties;
    }

    public URL getSeriesResourceURL(String relativePath)throws MalformedURLException {
        String url = appProperties.getApiDomain() + storeProperties.getPath() + relativePath;
        return new  URL(url);
    }
}
