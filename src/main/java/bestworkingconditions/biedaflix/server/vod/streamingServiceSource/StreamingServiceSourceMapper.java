package bestworkingconditions.biedaflix.server.vod.streamingServiceSource;

import bestworkingconditions.biedaflix.server.file.FileResourceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = FileResourceMapper.class)
public interface StreamingServiceSourceMapper {

    @Mapping(source = "logo", target = "path")
    StreamingServiceSourceResponse toDTO(StreamingServiceSource source);

    List<StreamingServiceSourceResponse> toDTOList(List<StreamingServiceSource> sources);
}
