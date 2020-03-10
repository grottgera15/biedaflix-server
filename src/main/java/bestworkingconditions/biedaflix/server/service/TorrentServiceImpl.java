package bestworkingconditions.biedaflix.server.service;


import bestworkingconditions.biedaflix.server.model.*;
import bestworkingconditions.biedaflix.server.repository.CurrentlyDownloadingRepository;
import bestworkingconditions.biedaflix.server.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.repository.SeriesRepository;
import bestworkingconditions.biedaflix.server.repository.TorrentUriRepository;
import bestworkingconditions.biedaflix.server.util.TorrentHttpEntityBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@EnableAsync
public class TorrentServiceImpl implements TorrentService {

    private final TorrentUriRepository torrentUriRepository;
    private final FileSystemResourceLoader fileSystemResourceLoader;
    private final CurrentlyDownloadingRepository currentlyDownloadingRepository;
    private final SeriesRepository seriesRepository;
    private final File filesystemRoot;
    private final EpisodeRepository episodeRepository;
    private final EpisodeService episodeService;
    private final RestTemplate restTemplate;

    Logger logger = LoggerFactory.getLogger(TorrentServiceImpl.class);

    public TorrentServiceImpl(TorrentUriRepository torrentUriRepository,
                              FileSystemResourceLoader fileSystemResourceLoader, CurrentlyDownloadingRepository currentlyDownloadingRepository,
                              SeriesRepository seriesRepository, File filesystemRoot, EpisodeRepository episodeRepository, EpisodeService episodeService, RestTemplate restTemplate) {
        this.torrentUriRepository = torrentUriRepository;
        this.fileSystemResourceLoader = fileSystemResourceLoader;
        this.currentlyDownloadingRepository = currentlyDownloadingRepository;
        this.seriesRepository = seriesRepository;
        this.filesystemRoot = filesystemRoot;
        this.episodeRepository = episodeRepository;
        this.episodeService = episodeService;
        this.restTemplate = restTemplate;
    }


    private Optional<File> getBiggestFileFromDirectory(CurrentlyDownloading currentlyDownloading) {

        if(currentlyDownloading.getTorrentFileInfoList().size() > 0) {

            TorrentFileInfo biggestFile = currentlyDownloading.getTorrentFileInfoList().get(0);

            for (TorrentFileInfo info : currentlyDownloading.getTorrentFileInfoList()) {
                if(info.getFileSize() > biggestFile.getFileSize())
                    biggestFile = info;
            }

            String path = System.getProperty("user.dir") + "/downloads/biedaflix_finished/" + biggestFile.getRelativePath();
            return Optional.of(new File(path));
        }

        return Optional.empty();
    }

    public static File getCurrentlyDownloadingDirectory(CurrentlyDownloading currentlyDownloading){

        TorrentFileInfo biggestFile = currentlyDownloading.getTorrentFileInfoList().stream().max(Comparator.comparingLong(TorrentFileInfo::getFileSize)).get();
        File file = new File(biggestFile.getRelativePath()).getParentFile();
        return new File(System.getProperty("user.dir") + "/downloads/biedaflix_finished/" + file.toString());
    }

    private void deleteLeftoverFilesFromDirectory(CurrentlyDownloading currentlyDownloading) throws IOException {
        if(currentlyDownloading.getTorrentFileInfoList().size() > 1){
            File parent = getCurrentlyDownloadingDirectory(currentlyDownloading);
            FileUtils.deleteDirectory(parent);
        }
        else
            FileUtils.forceDelete(new File(System.getProperty("user.dir") + "/downloads/biedaflix_finished/" + currentlyDownloading.getTorrentFileInfoList().get(0).getRelativePath() ));
    }

    public void normalizeRequestedFiles(CurrentlyDownloading currentlyDownloading) throws Exception {
        File renamer = fileSystemResourceLoader.getResource("renamer.sh").getFile();

        String[] commands = {
               renamer.getAbsolutePath(),"-i", System.getProperty("user.dir") + "/downloads/biedaflix_finished/"
        };

        ProcessBuilder processBuilder = new ProcessBuilder().command(commands).inheritIO();
        Process rename = processBuilder.start();
        rename.waitFor();

        renameTorrentFileInfos(currentlyDownloading);
        currentlyDownloadingRepository.save(currentlyDownloading);
    }

    public static void renameTorrentFileInfos(CurrentlyDownloading currentlyDownloading){
        for(TorrentFileInfo info : currentlyDownloading.getTorrentFileInfoList()){
            info.setRelativePath(info.getRelativePath().replaceAll("\\s+","_"));
        }
    }

    @Scheduled(initialDelay = 45000,fixedDelay = 30000)
    private void parseFinishedTorrents() throws Exception {

        List<CurrentlyDownloading> currentlyDownloadingList = currentlyDownloadingRepository.findAll();

        for(CurrentlyDownloading currentlyDownloading : currentlyDownloadingList){
        if(currentlyDownloading.getTorrentInfo().getProgress() ==  1) {

            logger.info("PREPARING TO RUN FFMPG");

            Series series = seriesRepository.findById(currentlyDownloading.getTarget()
                                                                          .getSeriesId()).get();

            normalizeRequestedFiles(currentlyDownloading);
            Optional<File> relativeVideoOptionalFile = getBiggestFileFromDirectory(currentlyDownloading);

            File relativeVideoFile = relativeVideoOptionalFile.orElseThrow(() -> new Exception("cannot find biggest file in directory"));

            File absoluteVideoFile = new File(relativeVideoFile.getAbsolutePath());

            File resource = fileSystemResourceLoader.getResource("ffmpeg-converter.sh").getFile();

            logger.info("VIDEO FILE" + "\"" + absoluteVideoFile.getAbsolutePath() + "\"");
            logger.info("ROOT " + filesystemRoot.getAbsolutePath() + "/series");

            List<String> commands = new ArrayList<>();
            commands.add(resource.getAbsolutePath());
            commands.add("-i");
            commands.add(absoluteVideoFile.getAbsolutePath());
            commands.add("-n");
            commands.add(series.getFolderName());
            commands.add("-e");
            commands.add(currentlyDownloading.getTarget().getId());
            commands.add("-d");
            commands.add(filesystemRoot.getAbsolutePath() + "/series");

            ProcessBuilder processBuilder = new ProcessBuilder().command(commands).inheritIO();

            try {
                Process process = processBuilder.start();
                process.waitFor();
            }
            catch (Exception e){
                throw new Exception(e);
            }

            logger.info("FFMPG COMPLETED");

            deleteTorrent(currentlyDownloading.getTorrentInfo().getHash(), true);

            List<EpisodeVideo> episodeVideos = new ArrayList<>();
            episodeVideos.add(new EpisodeVideo("mp4",series.getFolderName(),currentlyDownloading.getTarget().getId(),EpisodeVideo.VideoQuality.HIGH));
            episodeVideos.add(new EpisodeVideo("mp4",series.getFolderName(),currentlyDownloading.getTarget().getId(), EpisodeVideo.VideoQuality.MEDIUM));
            episodeVideos.add(new EpisodeVideo("mp4",series.getFolderName(),currentlyDownloading.getTarget().getId(), EpisodeVideo.VideoQuality.LOW));

            List<EpisodeThumbs> episodeThumbs = new ArrayList<>();

            try(Stream<Path> walk = Files.walk(Paths.get(filesystemRoot.getAbsolutePath() + "/series/" + series.getFolderName()
                        + "/"+ currentlyDownloading.getTarget().getId() +"/thumbs/"))){

                List<File> result = walk.filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());

                result.forEach(x ->  episodeThumbs.add(new EpisodeThumbs(FilenameUtils.getExtension(x.getName()),series.getFolderName(),
                        currentlyDownloading.getTarget().getId(),FilenameUtils.removeExtension(x.getName()))));


            }catch(IOException e){
                throw new IOException(e);
            }

            currentlyDownloading.getTarget().setVideoFiles(episodeVideos);
            currentlyDownloading.getTarget().setEpisodeThumbs(episodeThumbs);

            episodeRepository.save(currentlyDownloading.getTarget());

            deleteLeftoverFilesFromDirectory(currentlyDownloading);
            currentlyDownloadingRepository.delete(currentlyDownloading);
            }

        }
    }

    private void pauseDownloadedTorrents(List<TorrentInfo> status){
        List<String> hashes = new ArrayList<>();
        for (TorrentInfo info : status){
            if(info.getProgress() == 1)
                hashes.add(info.getHash());
        }

        if(hashes.size() > 0) {
            pauseTorrents(hashes);
            setTorrentCategory(hashes, TorrentCategory.BIEDAFLIX_FINISHED);
        }
    }

    @Scheduled(fixedDelay = 30000)
    private void checkTorrentsStatus() throws Exception {
        List<TorrentInfo> status = getTorrentsInfo();
        logger.info("SCHEDULED FUNCTION CALL " + status.toString());

        for( TorrentInfo info : status){
            CurrentlyDownloading currentlyDownloading = currentlyDownloadingRepository.findByTorrentInfo_Hash(info.getHash()).orElseThrow(() -> new Exception("TORRENT is missing "));
            currentlyDownloading.setTorrentFileInfoList( getFilesInfo(currentlyDownloading.getTorrentInfo().getHash()));
            currentlyDownloading.setTorrentInfo(info);
            currentlyDownloadingRepository.save(currentlyDownloading);
        }

        pauseDownloadedTorrents(status);
    }

    @Override
    public void addTorrent(String seriesName, String magnetLink , Episode episode) {

        episodeService.deleteVideoAndThumbs(episode);

        String seriesNameWithoutSpaces = seriesName.replaceAll("\\s+", "");
        String downloadName = seriesNameWithoutSpaces + "_S" + episode.getSeasonNumber() + "_E" + episode.getEpisodeNumber();

        HttpEntity<MultiValueMap<String,String>> request = new TorrentHttpEntityBuilder()
                .addKeyValuePair("urls",magnetLink)
                .addKeyValuePair("category","biedaflix")
                .addKeyValuePair("rename",downloadName)
                .addKeyValuePair("root_folder","true")
                .addKeyValuePair("autoTMM","true")
                .build();

        ResponseEntity<String> response = restTemplate.postForEntity(torrentUriRepository.getTorrentUri("add"),request,String.class);

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

        HttpEntity<MultiValueMap<String, String>> request = new TorrentHttpEntityBuilder(MediaType.APPLICATION_FORM_URLENCODED).addKeyValuePair("filter","all")
                                                                                          .addKeyValuePair("category","biedaflix").build();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(torrentUriRepository.getTorrentUri("info"))
                                                           .queryParam("filter","all");

        ResponseEntity<TorrentInfo[]> responseEntity  = restTemplate.getForEntity(builder.build().encode().toUriString(),TorrentInfo[].class,request);

        if(responseEntity.getBody() != null)
            return new ArrayList<>(Arrays.asList(responseEntity.getBody()));
        else
            return new ArrayList<>();
    }

    @Override
    public void deleteTorrent(String torrentHash,boolean deleteFiles) {
        HttpEntity<MultiValueMap<String, String>> request = new TorrentHttpEntityBuilder(MediaType.APPLICATION_FORM_URLENCODED).addKeyValuePair("hashes", torrentHash)
                                                                                          .addKeyValuePair("deleteFiles", Boolean.toString(deleteFiles))
                                                                                          .build();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(torrentUriRepository.getTorrentUri("delete"),request, String.class);
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

        HttpEntity<MultiValueMap<String, String>> request = new TorrentHttpEntityBuilder(MediaType.APPLICATION_FORM_URLENCODED).addKeyValuePair("hashes",combinedHashes).build();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(torrentUriRepository.getTorrentUri("pause"),request,String.class);
    }

    @Override
    public void resumeTorrents(List<String> torrentHashes) {
        String combineHashes = combineHashesForRequest(torrentHashes);

        HttpEntity<MultiValueMap<String, String>> request = new TorrentHttpEntityBuilder(MediaType.APPLICATION_FORM_URLENCODED).addKeyValuePair("hashes",combineHashes).build();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(torrentUriRepository.getTorrentUri("resume"),request,String.class);
    }

    @Override
    public void setTorrentCategory(List<String> torrentHashes, TorrentCategory category) {
        String combinedHashes = combineHashesForRequest(torrentHashes);

        HttpEntity<MultiValueMap<String,String>> request = new TorrentHttpEntityBuilder(MediaType.APPLICATION_FORM_URLENCODED)
                .addKeyValuePair("hashes",combinedHashes)
                .addKeyValuePair("category",category.getCategoryValue()).build();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(torrentUriRepository.getTorrentUri("setCategory"),request,String.class);
    }

    @Override
    public List<TorrentFileInfo> getFilesInfo(@NotBlank  String torrentHash) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(torrentUriRepository.getTorrentUri("files"))
                .queryParam("hash",torrentHash);

        HttpEntity<MultiValueMap<String,String>> request = new TorrentHttpEntityBuilder().build();

        ResponseEntity<TorrentFileInfo[]> response = restTemplate.getForEntity(builder.build().encode().toUriString(),TorrentFileInfo[].class,request);

        if(response.getBody() != null)
            return new ArrayList<>(Arrays.asList(response.getBody()));
        else
            return new ArrayList<>();
    }

}
