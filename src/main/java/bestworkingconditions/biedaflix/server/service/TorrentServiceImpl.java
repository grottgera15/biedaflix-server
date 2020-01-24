package bestworkingconditions.biedaflix.server.service;


import bestworkingconditions.biedaflix.server.model.*;
import bestworkingconditions.biedaflix.server.model.request.EpisodeRequest;
import bestworkingconditions.biedaflix.server.properties.TorrentProperties;
import bestworkingconditions.biedaflix.server.repository.CurrentlyDownloadingRepository;
import bestworkingconditions.biedaflix.server.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.repository.SeriesRepository;
import bestworkingconditions.biedaflix.server.repository.TorrentUriRepository;
import bestworkingconditions.biedaflix.server.util.TorrentHttpEntityBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.graalvm.compiler.nodes.memory.MemoryCheckpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.http.HttpEntity;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@EnableAsync
public class TorrentServiceImpl implements TorrentService {

    private final TorrentUriRepository torrentUriRepository;
    private final TorrentProperties torrentProperties;
    private final FileSystemResourceLoader fileSystemResourceLoader;
    private final CurrentlyDownloadingRepository currentlyDownloadingRepository;
    private final SeriesRepository seriesRepository;
    private final File filesystemRoot;
    private final EpisodeRepository episodeRepository;

    Logger logger = LoggerFactory.getLogger(TorrentServiceImpl.class);

    public TorrentServiceImpl(TorrentUriRepository torrentUriRepository, TorrentProperties torrentProperties, FileSystemResourceLoader fileSystemResourceLoader, CurrentlyDownloadingRepository currentlyDownloadingRepository, SeriesRepository seriesRepository, File filesystemRoot, EpisodeRepository episodeRepository) {
        this.torrentUriRepository = torrentUriRepository;
        this.torrentProperties = torrentProperties;
        this.fileSystemResourceLoader = fileSystemResourceLoader;
        this.currentlyDownloadingRepository = currentlyDownloadingRepository;
        this.seriesRepository = seriesRepository;
        this.filesystemRoot = filesystemRoot;
        this.episodeRepository = episodeRepository;
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

    private void deleteLeftoverFilesFromDirectory(CurrentlyDownloading currentlyDownloading) throws IOException {
        if(currentlyDownloading.getTorrentFileInfoList().size() > 0){
            File parent = new File(System.getProperty("user.dir") + "/downloads/biedaflix_finished/" + currentlyDownloading.getTorrentFileInfoList().get(0)).getParentFile();
            FileUtils.deleteDirectory(parent);
        }
        else
            FileUtils.deleteDirectory(new File(System.getProperty("user.dir") + currentlyDownloading.getTorrentFileInfoList().get(0).getRelativePath() ));
    }

    private void normalizeRequestedFiles(CurrentlyDownloading currentlyDownloading) throws Exception {
        File renamer = fileSystemResourceLoader.getResource("renamer.sh").getFile();

        String[] commands = {
               renamer.getAbsolutePath(),"-i", System.getProperty("user.dir") + "/downloads/biedaflix_finished/"
        };

        ProcessBuilder processBuilder = new ProcessBuilder().command(commands).inheritIO();
        Process rename = processBuilder.start();
        rename.waitFor();

        for(TorrentFileInfo info : currentlyDownloading.getTorrentFileInfoList()){
            if(currentlyDownloading.getTorrentFileInfoList().size() > 1)
                info.setRelativePath("/" + info.getRelativePath().replaceAll("//s+","_"));
            else
                info.setRelativePath(info.getRelativePath().replaceAll("//s+","_"));
        }
    }


    @Scheduled(initialDelay = 15000,fixedRate = 30000)
    private void parseFinishedTorrents() throws Exception {

        List<CurrentlyDownloading> currentlyDownloadingList = currentlyDownloadingRepository.findAll();

        for(CurrentlyDownloading currentlyDownloading : currentlyDownloadingList){
        if(currentlyDownloading.getTorrentInfo().getProgress() ==  1) {

            logger.info("PREPARING TO RUN FFMPG");

            Series series = seriesRepository.findById(currentlyDownloading.getTarget()
                                                                          .getSeriesId()).get();

            normalizeRequestedFiles(currentlyDownloading);
            Optional<File> relativeVideoOptionaloFile = getBiggestFileFromDirectory(currentlyDownloading);

            File relativeVideoFile = relativeVideoOptionaloFile.orElseThrow(() -> new Exception("cannot find biggest file in directory"));

            File aboluteVideoFile = new File(relativeVideoFile.getAbsolutePath());

            File resource = fileSystemResourceLoader.getResource("ffmpeg-converter.sh").getFile();

            logger.info("VIDEO FILE" + "\"" + aboluteVideoFile.getAbsolutePath() + "\"");
            logger.info("ROOT " + filesystemRoot.getAbsolutePath() + "/series");

            List<String> commands = new ArrayList<>();
            commands.add(resource.getAbsolutePath());
            commands.add("-i");
            commands.add(aboluteVideoFile.getAbsolutePath());
            commands.add("-n");
            commands.add(series.getFolderName());
            commands.add("-s");
            commands.add(Integer.toString(currentlyDownloading.getTarget()
                                                              .getSeasonNumber()));
            commands.add("-e");
            commands.add(Integer.toString(currentlyDownloading.getTarget()
                                                              .getEpisodeNumber()));
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

            //FIXME: ADD EPISODE TO DATABASE PROPERLY

            List<EpisodeVideo> episodeVideos = new ArrayList<>();
            episodeVideos.add(new EpisodeVideo("mp4",series.getFolderName(),currentlyDownloading.getTarget().getSeasonNumber(),currentlyDownloading.getTarget().getEpisodeNumber(), EpisodeVideo.VideoQuality.HIGH));
            episodeVideos.add(new EpisodeVideo("mp4",series.getFolderName(),currentlyDownloading.getTarget().getSeasonNumber(),currentlyDownloading.getTarget().getEpisodeNumber(), EpisodeVideo.VideoQuality.MEDIUM));
            episodeVideos.add(new EpisodeVideo("mp4",series.getFolderName(),currentlyDownloading.getTarget().getSeasonNumber(),currentlyDownloading.getTarget().getEpisodeNumber(), EpisodeVideo.VideoQuality.LOW));

            List<EpisodeThumbs> episodeThumbs = new ArrayList<>();

            try(Stream<Path> walk = Files.walk(Paths.get(filesystemRoot.getAbsolutePath() + "/series/" + series.getFolderName()
                        + "/s" + currentlyDownloading.getTarget().getSeasonNumber() + "/e" + currentlyDownloading.getTarget().getEpisodeNumber() +"/thumbs/"))){


                List<File> result = walk.filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());

                result.forEach(x ->  episodeThumbs.add(new EpisodeThumbs(FilenameUtils.getExtension(x.getName()),series.getFolderName(),
                        currentlyDownloading.getTarget().getSeasonNumber(),currentlyDownloading.getTarget().getEpisodeNumber(),FilenameUtils.removeExtension(x.getName()))));


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

    @Scheduled(fixedRate = 15000)
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
    public void addTorrent(String seriesName, EpisodeRequest episodeRequest , Episode episode) {

        String seriesNameWithoutSpaces = seriesName.replaceAll("\\s+", "");
        String downloadName = seriesNameWithoutSpaces + "_S" + episodeRequest.getSeasonNumber() + "_E" + episodeRequest.getEpisodeNumber();

        HttpEntity<MultiValueMap<String,String>> request = new TorrentHttpEntityBuilder()
                .addKeyValuePair("urls",episodeRequest.getMagnetLink())
                .addKeyValuePair("category","biedaflix")
                .addKeyValuePair("rename",downloadName)
                .addKeyValuePair("root_folder","true")
                .build();

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
    public void setTorrentCategory(List<String> torrentHashes, TorrentCategory category) {
        String combinedHashes = combineHashesForRequest(torrentHashes);

        HttpEntity<MultiValueMap<String,String>> request = new TorrentHttpEntityBuilder()
                .addKeyValuePair("hashes",combinedHashes)
                .addKeyValuePair("category",category.getCategoryValue()).build();

        ResponseEntity<String> responseEntity = new RestTemplate().postForEntity(torrentUriRepository.getUri("setCategory"),request,String.class);
    }

    @Override
    public List<TorrentFileInfo> getFilesInfo(@NotBlank  String torrentHash) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(torrentUriRepository.getUri("files"))
                .queryParam("hash",torrentHash);

        ResponseEntity<TorrentFileInfo[]> response = new RestTemplate().getForEntity(builder.build().encode().toUri(),TorrentFileInfo[].class);

        if(response.getBody() != null)
            return new ArrayList<>(Arrays.asList(response.getBody()));
        else
            return new ArrayList<>();
    }

}
