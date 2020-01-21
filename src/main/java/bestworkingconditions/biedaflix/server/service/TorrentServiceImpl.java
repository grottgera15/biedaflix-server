package bestworkingconditions.biedaflix.server.service;


import bestworkingconditions.biedaflix.server.model.*;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;
import bestworkingconditions.biedaflix.server.properties.TorrentProperties;
import bestworkingconditions.biedaflix.server.repository.CurrentlyDownloadingRepository;
import bestworkingconditions.biedaflix.server.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.repository.SeriesRepository;
import bestworkingconditions.biedaflix.server.repository.TorrentUriRepository;
import bestworkingconditions.biedaflix.server.util.TorrentHttpEntityBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.*;

@Service
@EnableAsync
public class TorrentServiceImpl implements TorrentService {

    private final TorrentUriRepository torrentUriRepository;
    private final TorrentProperties torrentProperties;
    private final ResourceLoader resourceLoader;
    private final CurrentlyDownloadingRepository currentlyDownloadingRepository;
    private final SeriesRepository seriesRepository;
    private final File filesystemRoot;
    private final EpisodeRepository episodeRepository;

    Logger logger = LoggerFactory.getLogger(TorrentServiceImpl.class);

    private List<TorrentInfo> finishedDownloading = new ArrayList<>();

    @Autowired
    public TorrentServiceImpl(TorrentUriRepository torrentUriRepository,
                              TorrentProperties torrentProperties,
                              @Qualifier("fileSystemResourceLoader") ResourceLoader resourceLoader,
                              CurrentlyDownloadingRepository currentlyDownloadingRepository,
                              SeriesRepository seriesRepository,
                              File filesystemRoot, EpisodeRepository episodeRepository) {
        this.torrentUriRepository = torrentUriRepository;
        this.torrentProperties = torrentProperties;
        this.resourceLoader = resourceLoader;
        this.currentlyDownloadingRepository = currentlyDownloadingRepository;
        this.seriesRepository = seriesRepository;
        this.filesystemRoot = filesystemRoot;
        this.episodeRepository = episodeRepository;
    }

    private File getBiggestFileFromDirectory(List<TorrentFileInfo> torrentFilesInfo) throws Exception {

        if(torrentFilesInfo.size() > 0) {

            TorrentFileInfo biggestFile = torrentFilesInfo.get(0);

            for (TorrentFileInfo info : torrentFilesInfo) {
                if(info.getFileSize() > biggestFile.getFileSize())
                    biggestFile = info;
            }
            File torrentFile = new ClassPathResource(torrentProperties.getDownloadPath() + biggestFile.getRelativePath()).getFile();
            return torrentFile;
        }

        return null;
    }

    @Scheduled(initialDelay = 30000,fixedRate = 60000)
    private void parseFinishedTorrents() throws Exception {
        if(finishedDownloading.size() > 0) {
            TorrentInfo torrentToParse = finishedDownloading.get(0);

            Optional<CurrentlyDownloading> currentlyDownloadingOptional = currentlyDownloadingRepository.findByTorrentInfo_Hash(torrentToParse.getHash());

            if(currentlyDownloadingOptional.isPresent()) {

                logger.info("PREPARING TO RUN FFMPG");

                CurrentlyDownloading currentlyDownloading = currentlyDownloadingOptional.get();

                Series series = seriesRepository.findById(currentlyDownloading.getTarget()
                                                                              .getSeriesId()).get();

                File videoFile = getBiggestFileFromDirectory(getFilesInfo(currentlyDownloading.getTorrentInfo().getHash()));

                Resource resource = resourceLoader.getResource("classpath:ffmpeg-converter.sh");

                List<String> commands = new ArrayList<>();
                commands.add("bash");
                commands.add(resource.getFile()
                                     .getAbsolutePath());
                commands.add("-i");
                commands.add( videoFile.toString());
                commands.add("-n");
                commands.add(series.getName());
                commands.add("-s");
                commands.add(Integer.toString(currentlyDownloading.getTarget()
                                                                  .getSeasonNumber()));
                commands.add("-e");
                commands.add(Integer.toString(currentlyDownloading.getTarget()
                                                                  .getEpisodeNumber()));
                commands.add("-d");
                commands.add(filesystemRoot.getAbsolutePath());

                Process processBuilder = new ProcessBuilder().command(commands)
                                                             .start();

                processBuilder.waitFor();
                logger.info("FFMPG COMPLETED");

                deleteTorrent(torrentToParse.getHash(), true);

                //FIXME: ADD EPISODE TO DATABASE PROPERLY
                episodeRepository.save(currentlyDownloading.getTarget());

                currentlyDownloadingRepository.delete(currentlyDownloading);
            }
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    private void pauseDownloadedTorrents(){
        List<TorrentInfo> status = getTorrentsInfo();
        logger.info("SCHEDULED FUNCTION CALL " + status.toString());

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
    public void addTorrent(String seriesName, EpisodeRequest episodeRequest , Episode episode) {

        String seriesNameWithoutSpaces = seriesName.replaceAll("\\s+", "");
        String downloadName = seriesNameWithoutSpaces + "_S" + episodeRequest.getSeasonNumber() + "_E" + episodeRequest.getEpisodeNumber();

        HttpEntity<MultiValueMap<String,String>> request = new TorrentHttpEntityBuilder()
                .addKeyValuePair("urls",episodeRequest.getMagnetLink())
                .addKeyValuePair("category","biedaflix")
                .addKeyValuePair("rename",downloadName).build();

        ResponseEntity<String> response = new RestTemplate().postForEntity(torrentUriRepository.getUri("add"),request,String.class);

        List<TorrentInfo> torrentsInfo = getTorrentsInfo();
        logger.info("TORRENTS" + String.valueOf(torrentsInfo));

        for(TorrentInfo info : torrentsInfo){
            logger.info("INFO GET NAME :" + info.getName());
            if(info.getName().equals(downloadName)){
                logger.info("HELLO THERE");
                CurrentlyDownloading currentlyDownloading = new CurrentlyDownloading();
                currentlyDownloading.setTarget(episode);
                currentlyDownloading.setTorrentInfo(info);
                currentlyDownloadingRepository.save(currentlyDownloading);
            }
        }

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

    @Override
    public List<TorrentFileInfo> getFilesInfo(@NotBlank  String torrentHash) {
        HttpEntity<MultiValueMap<String,String>> request = new TorrentHttpEntityBuilder(MediaType.APPLICATION_JSON).addKeyValuePair("hash", torrentHash).build();

        ResponseEntity<TorrentFileInfo[]> response = new RestTemplate().getForEntity(torrentUriRepository.getUri("files"),TorrentFileInfo[].class,request);

        if(response.getBody() != null)
            return new ArrayList<>(Arrays.asList(response.getBody()));
        else
            return new ArrayList<>();
    }

}
