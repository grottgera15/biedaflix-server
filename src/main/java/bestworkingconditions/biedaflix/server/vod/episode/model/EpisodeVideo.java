package bestworkingconditions.biedaflix.server.vod.episode.model;

import bestworkingconditions.biedaflix.server.file.FileResource;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeVideo extends FileResource {
    private VideoQuality videoQuality;
}


