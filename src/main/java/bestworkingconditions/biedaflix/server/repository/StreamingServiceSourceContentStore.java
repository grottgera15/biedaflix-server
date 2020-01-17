package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.StreamingServiceSource;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamingServiceSourceContentStore extends ContentStore<StreamingServiceSource,String> {
}
