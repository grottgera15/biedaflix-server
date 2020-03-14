package bestworkingconditions.biedaflix.server.common.service;

import bestworkingconditions.biedaflix.server.torrent.model.CurrentlyDownloading;
import bestworkingconditions.biedaflix.server.torrent.model.TorrentFileInfo;
import bestworkingconditions.biedaflix.server.torrent.TorrentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class TorrentServiceImplTest {


    TorrentServiceImpl torrentService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

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
    }
}