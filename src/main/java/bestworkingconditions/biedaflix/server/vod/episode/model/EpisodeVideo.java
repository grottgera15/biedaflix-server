package bestworkingconditions.biedaflix.server.vod.episode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeVideo {

    public enum VideoQuality{
        HIGH("1080"),
        MEDIUM("720"),
        LOW("480");

        private String quality;

        VideoQuality(String quality) {
            this.quality = quality;
        }

        public String getQuality() {
            return quality;
        }
    }

    private VideoQuality videoQuality;

}


