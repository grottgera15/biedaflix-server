package bestworkingconditions.biedaflix.server.file;

import org.springframework.content.commons.repository.ContentStore;
import org.springframework.content.rest.StoreRestResource;
import org.springframework.stereotype.Repository;

@Repository
@StoreRestResource(path = "/files/")
public interface FileResourceContentStore extends ContentStore<FileResource,String> {
}
