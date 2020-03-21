package bestworkingconditions.biedaflix.server.vod.episode.eventListener;

import bestworkingconditions.biedaflix.server.vod.episode.model.Episode;
import bestworkingconditions.biedaflix.server.vod.episode.model.EpisodeStatus;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class EpisodeListener extends AbstractMongoEventListener<Episode> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Episode> event) {
        Episode ep = event.getSource();

        if(ep.getSubtitles().size() > 0 && ep.getVideos().size() > 0){
            ep.setStatus(EpisodeStatus.AVAILABLE);
        }else if(ep.getVideos().size() > 0){
            ep.setStatus(EpisodeStatus.NO_SUBTITLES);
        }else
            ep.setStatus(EpisodeStatus.UNAVAILABLE);
    }
}
