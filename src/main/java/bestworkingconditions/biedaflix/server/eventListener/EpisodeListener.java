package bestworkingconditions.biedaflix.server.eventListener;

import bestworkingconditions.biedaflix.server.model.Episode;
import bestworkingconditions.biedaflix.server.model.EpisodeStatus;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class EpisodeListener extends AbstractMongoEventListener<Episode> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Episode> event) {
        Episode ep = event.getSource();

        if(ep.getEpisodeSubtitles().size() > 0 && ep.getVideoFiles().size() > 0){
            ep.setEpisodeStatus(EpisodeStatus.AVAILABLE);
        }else if(ep.getVideoFiles().size() > 0){
            ep.setEpisodeStatus(EpisodeStatus.NO_SUBTITLES);
        }else
            ep.setEpisodeStatus(EpisodeStatus.UNAVAILABLE);
    }
}
