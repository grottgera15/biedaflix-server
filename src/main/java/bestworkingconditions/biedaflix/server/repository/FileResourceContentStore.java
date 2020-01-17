package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.FileResource;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.stereotype.Repository;

@Repository
public interface FileResourceContentStore extends ContentStore<FileResource,String> {
}
