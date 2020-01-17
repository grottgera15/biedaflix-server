package bestworkingconditions.biedaflix.server.service;


import bestworkingconditions.biedaflix.server.model.TorrentInfo;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;
import bestworkingconditions.biedaflix.server.repository.FileContentStore;
import bestworkingconditions.biedaflix.server.repository.TorrentUriRepository;
import bestworkingconditions.biedaflix.server.util.TorrentHttpEntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TorrentServiceImpl implements TorrentService {

    private final TorrentUriRepository torrentUriRepository;

    Logger logger = LoggerFactory.getLogger(TorrentServiceImpl.class);

    @Autowired
    public TorrentServiceImpl(TorrentUriRepository torrentUriRepository) {this.torrentUriRepository = torrentUriRepository;}

    @Scheduled(cron = "0 0/1 * * * ?")
    private void CheckTorrentsStatus(){
        List<TorrentInfo> status = getTorrentsInfo();
        logger.info("SCHEDULED FUNCTION CALL " + status.toString());

       List<TorrentInfo> finishedDownloading = new ArrayList<>();

        for( TorrentInfo info : status){
            if(info.getProgress() == 1.0){
                finishedDownloading.add(info);
            }
        }

        List<String> hashes = new ArrayList<>();
        for (TorrentInfo info : finishedDownloading){
            hashes.add(info.getHash());
        }

        pauseTorrents(hashes);
    }

    @Override
    public void addTorrent(String seriesName,EpisodeRequest episodeRequest) {

        String seriesNameWithoutSpaces = seriesName.replaceAll("\\s+", "");
        String downloadName = seriesNameWithoutSpaces + "_S" + episodeRequest.getSeasonNumber() + "_E" + episodeRequest.getEpisodeNumber();

        HttpEntity<MultiValueMap<String,String>> request = new TorrentHttpEntityBuilder()
                .addKeyValuePair("urls",episodeRequest.getMagnetLink())
                .addKeyValuePair("category","biedaflix")
                .addKeyValuePair("rename",downloadName).build();

        ResponseEntity<String> response = new RestTemplate().postForEntity(torrentUriRepository.getUri("add"),request,String.class);

    }

    @Override
    public List<TorrentInfo> getTorrentsInfo() {

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

    private String combineHashesForRequest(List<String> torrentHashes){
        StringBuilder responseBuilder = new StringBuilder();
        for ( int i = 0; i < torrentHashes.size(); i++ ){
            String hash = torrentHashes.get(i);

            if(i != 0)
                responseBuilder.append("|").append(hash);
            else
                responseBuilder.append(hash);
        }

        return responseBuilder.toString();
    }

    @Override
    public void pauseTorrents(List<String> torrentHashes) {
        String combinedHashes = combineHashesForRequest(torrentHashes);

        HttpEntity<MultiValueMap<String, String>> request = new TorrentHttpEntityBuilder().addKeyValuePair("hashes",combinedHashes).build();
        ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(torrentUriRepository.getUri("pause"),request,String.class);
    }

    @Override
    public void resumeTorrents(List<String> torrentHashes) {
        String combineHashes = combineHashesForRequest(torrentHashes);

        HttpEntity<MultiValueMap<String, String>> request = new TorrentHttpEntityBuilder().addKeyValuePair("hashes",combineHashes).build();
        ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(torrentUriRepository.getUri("resume"),request,String.class);
    }

}
