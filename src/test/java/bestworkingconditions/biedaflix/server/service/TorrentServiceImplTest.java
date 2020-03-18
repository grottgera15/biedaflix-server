package bestworkingconditions.biedaflix.server.service;

import bestworkingconditions.biedaflix.server.model.CurrentlyDownloading;
import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.Series;
import bestworkingconditions.biedaflix.server.model.TorrentFileInfo;
import bestworkingconditions.biedaflix.server.model.TorrentInfo;
import bestworkingconditions.biedaflix.server.repository.CurrentlyDownloadingRepository;
import bestworkingconditions.biedaflix.server.repository.EpisodeRepository;
import bestworkingconditions.biedaflix.server.repository.SeriesRepository;
import bestworkingconditions.biedaflix.server.repository.TorrentUriRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.doReturn;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

class TorrentServiceImplTest {

    TorrentServiceImpl torrentService;

    @Mock
    CurrentlyDownloadingRepository currentlyDownloadingRepository;

    @Mock
    SeriesRepository seriesRepository;

    @Mock
    TorrentUriRepository torrentUriRepository;

    @Mock
    EpisodeService episodeService;

    @Mock
    EpisodeRepository episodeRepository;

    @Mock
    RestTemplate restTemplate;

    @Mock
    TorrentInfo torrentInfo;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader("files/");
        File filesystemRoot = new File(System.getProperty("user.dir") + "/files");
        torrentService = new TorrentServiceImpl(torrentUriRepository, fileSystemResourceLoader, currentlyDownloadingRepository, seriesRepository, filesystemRoot, episodeRepository, episodeService, restTemplate);
    }

    @Test
    void parseFinishedTorrentsExecutorTest() throws Exception {
        //tworze obiekt currentlyDownloading
        CurrentlyDownloading currentlyDownloading = new CurrentlyDownloading();
        //currentlyDownloading ma informacje o torrencie
        currentlyDownloading.setTorrentInfo(torrentInfo);
        //zwracam 1 jeśli wywołuje getProgress z TorrentInfo
        doReturn(1f).when(torrentInfo).getProgress();
        //tworze nowy Epizod i dodaje to targetu w currentlyDownloading
        String seriesId = "5e68f9dd215c856ec935cc42";
        Episode episode = new Episode(seriesId, 1, 1, "lol", new Date(1383628031));
        episode.setId("1999");
        currentlyDownloading.setTarget(episode);
        //jesli wywołuje seriesRepository findById z danego id to zwracam przygotowany series
        Series series = new Series();
        series.setId(seriesId);
        Optional<Series> seriesOptional = Optional.of(series);
        doReturn(seriesOptional).when(seriesRepository).findById(seriesId);
        //potem wywołuje normalizeRequestedFiles, które wywołuje renameTorrentFileInfos i tutaj potrzebuje torrentFileInfo
        //torentFileInfo odnosi się do pliku mkv który stworzyłem!
        String relativePath = "test/lol.mkv";
        Long fileSize = 2712067l;
        TorrentFileInfo torrentFileInfo = new TorrentFileInfo(relativePath, fileSize);
        List<TorrentFileInfo> torrentFileInfos = new ArrayList<>();
        torrentFileInfos.add(torrentFileInfo);
        currentlyDownloading.setTorrentFileInfoList(torrentFileInfos);
        //potem curentllyDownloading probuje to zapisac do bazy
        doReturn(currentlyDownloading).when(currentlyDownloadingRepository).save(currentlyDownloading);
        //potem bierze najwiekszy plik z folderu, nie powinno sie wyjebac
        //w tym momencie powinno się wywołać konwertowanie
        //teraz powinno się wyjebać na deleteTorrent

        List<CurrentlyDownloading> currentlyDownloadingList = new ArrayList<>();
        currentlyDownloadingList.add(currentlyDownloading);
        doReturn(currentlyDownloadingList).when(currentlyDownloadingRepository).findAll();

        //mozna to pozniej jakos ladnie robic zeby testowal funkcje scheduled
        //pytanie czy torrentService sie nie wypierdoli bo sa w niej te scheduled funkcje
        
        torrentService.parseFinishedTorrentsExecutor();
    }

    /*
    @Test
    void renameTorrentFileInfosTest_withMultipleFiles() {

        CurrentlyDownloading currentlyDownloading = new CurrentlyDownloading();
        List<TorrentFileInfo> torrentFileInfoList =  new ArrayList<>();
        torrentFileInfoList.add(new TorrentFileInfo("The Mandalorian S01E02 1080p WEBRip x264 AAC 5.1 ESubs - LOKiHD - " +
                "Telly/The Mandalorian S01E02 1080p WEBRip x264 AAC 5.1 ESubs - LOKiHD - Telly.mkv", 2482941293L));

        torrentFileInfoList.add(new TorrentFileInfo("The_Mandalorian S01E02 1080p WEBRip x264 AAC 5.1 ESubs - LOKiHD - Telly/Screens/screen0004.png",1361107));

        currentlyDownloading.setTorrentFileInfoList(torrentFileInfoList);

        List<TorrentFileInfo> properList =  new ArrayList<>();
        properList.add(new TorrentFileInfo("The_Mandalorian_S01E02_1080p_WEBRip_x264_AAC_5.1_ESubs_-_LOKiHD_-_" +
                "Telly/The_Mandalorian_S01E02_1080p_WEBRip_x264_AAC_5.1_ESubs_-_LOKiHD_-_Telly.mkv", 2482941293L));
        properList.add(new TorrentFileInfo("The_Mandalorian_S01E02_1080p_WEBRip_x264_AAC_5.1_ESubs_-_LOKiHD_-_Telly/Screens/screen0004.png",1361107));

        TorrentServiceImpl.renameTorrentFileInfos(currentlyDownloading);

        Assert.assertEquals(properList.get(0).getRelativePath(),currentlyDownloading.getTorrentFileInfoList().get(0).getRelativePath());
        Assert.assertEquals(properList.get(1).getRelativePath(),currentlyDownloading.getTorrentFileInfoList().get(1).getRelativePath());
    }

    @Test
    void getCurrentlyDownloadingDirectory() {

        CurrentlyDownloading currentlyDownloading = new CurrentlyDownloading();

        List<TorrentFileInfo> torrentFileInfoList =  new ArrayList<>();
        torrentFileInfoList.add(new TorrentFileInfo("The_Mandalorian_S01E02_1080p_WEBRip_x264_AAC_5.1_ESubs_-_LOKiHD_-_" +
                "Telly/The_Mandalorian_S01E02_1080p_WEBRip_x264_AAC_5.1_ESubs_-_LOKiHD_-_Telly.mkv", 2482941293L));

        torrentFileInfoList.add(new TorrentFileInfo("The_Mandalorian_S01E02_1080p_WEBRip_x264_AAC_5.1_ESubs_-_LOKiHD_-_Telly/Screens/screen0004.png",1361107));

        currentlyDownloading.setTorrentFileInfoList(torrentFileInfoList);

        File parentFile = new File(System.getProperty("user.dir") + "/downloads/biedaflix_finished/" + "The_Mandalorian_S01E02_1080p_WEBRip_x264_AAC_5.1_ESubs_-_LOKiHD_-_Telly/");

        Assert.assertEquals(parentFile, TorrentServiceImpl.getCurrentlyDownloadingDirectory(currentlyDownloading));
    } */
}