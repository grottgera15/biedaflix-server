package bestworkingconditions.biedaflix.server.repository;

import org.springframework.content.commons.repository.Store;
import org.springframework.content.rest.StoreRestResource;
import org.springframework.stereotype.Repository;

@Repository
@StoreRestResource(path="files")
public interface FileContentStore extends Store<String> {
}
