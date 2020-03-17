package bestworkingconditions.biedaflix.server.vod.hdd;

import bestworkingconditions.biedaflix.server.vod.episode.model.Episode;
import bestworkingconditions.biedaflix.server.vod.episode.repository.EpisodeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.mockito.Mockito.doReturn;

class HddControllerTest {
    private static final double DELTA = 1e-15;

    HddController hddController;

    @Mock
            EpisodeRepository episodeRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        hddController = new HddController(episodeRepository);
    }

    @Test
    void getAllEpisodesSizeTest() {
        Episode episode = new Episode("5e68f9dd215c856ec935cc42", 1, 1, "lol", new Date(1383628031));
        episode.setSize(0.3);
        List<Episode> episodes = new ArrayList<>();
        episodes.add(episode);
        doReturn(episodes).when(episodeRepository).findAll();

        Assert.assertEquals(0.3, hddController.getAllEpisodesSize(), DELTA);
    }
}