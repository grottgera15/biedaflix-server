package bestworkingconditions.biedaflix.server.controller;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.repository.EpisodeRepository;


class HddStatusTest {
    private static final double DELTA = 1e-15;

    HddController hddController;

    @Mock //z mock bean sie nie inicjalizowalo i bylo null
    EpisodeRepository episodeRepository;

    @BeforeEach //z samym before sie nie robilo
    public void init() {
        MockitoAnnotations.initMocks(this);
        hddController = new HddController(episodeRepository); //tego nie bylo i hddcontroller byl nullem
    }

    @Test
    void getAllEpisodesSizeTest() {
        Episode episode = new Episode("5e68f9dd215c856ec935cc42", 1, 1, "lol", new Date(1383628031));
        episode.setSize(0.3);
        //jesli chce wywolac find all to zwracam jeden epizod w liscie
        List<Episode> episodes = new ArrayList<>();
        episodes.add(episode);
        doReturn(episodes).when(episodeRepository).findAll();
        //when(episodeRepository.findAll()).thenReturn(episodes); //na tym sie wypierdala

        Assert.assertEquals(0.3, hddController.getAllEpisodesSize(), DELTA);
    }

}