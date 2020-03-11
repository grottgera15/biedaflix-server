package bestworkingconditions.biedaflix.server.service;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.repository.EpisodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

class EpisodeServiceTest {

    @Mock
    private SeriesService seriesServiceMock;

    @Mock
    private EpisodeRepository episodeRepositoryMock;

    @InjectMocks
    private EpisodeService episodeService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getNextEpisodeTest_sameSeason() {

        Episode testEpisode = new Episode();
        testEpisode.setSeriesId("5e678eb5e68e5e19618290ff");
        testEpisode.setSeasonNumber(1);
        testEpisode.setEpisodeNumber(3);

    }
}