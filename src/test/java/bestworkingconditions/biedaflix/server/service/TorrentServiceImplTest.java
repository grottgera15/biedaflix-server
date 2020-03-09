package bestworkingconditions.biedaflix.server.service;

import bestworkingconditions.biedaflix.server.model.CurrentlyDownloading;
import bestworkingconditions.biedaflix.server.model.TorrentFileInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.AssertTrue;
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
    void example_test(){
        Assert.assertTrue(true);
    }

    @Test
    void getCurrentlyDownloadingDirectory() {

        CurrentlyDownloading currentlyDownloading = new CurrentlyDownloading();

        List<TorrentFileInfo> torrentFileInfoList =  new ArrayList<>();
        torrentFileInfoList.add(new TorrentFileInfo("The_Mandalorian_S01E02_1080p_WEBRip_x264_AAC_5.1_ESubs_-_LOKiHD_-_" +
                "Telly/The_Mandalorian_S01E02_1080p_WEBRip_x264_AAC_5.1_ESubs_-_LOKiHD_-_Telly.mkv", 746577956L));

        currentlyDownloading.setTorrentFileInfoList(torrentFileInfoList);

        File parentFile = new File(System.getProperty("user.dir") + "/downloads/biedaflix_finished/" + "The_Mandalorian_S01E02_1080p_WEBRip_x264_AAC_5.1_ESubs_-_LOKiHD_-_Telly/");

        Assert.assertEquals(parentFile, TorrentServiceImpl.getCurrentlyDownloadingDirectory(currentlyDownloading));
    }
}