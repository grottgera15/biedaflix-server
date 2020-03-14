package bestworkingconditions.biedaflix.server.common.repository;

import bestworkingconditions.biedaflix.server.common.model.FileResource;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.stereotype.Repository;

@Repository
public interface FileResourceContentStore extends ContentStore<FileResource,String> {
}
