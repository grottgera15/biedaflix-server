package bestworkingconditions.biedaflix.server.common.repository;

import org.springframework.content.commons.repository.Store;
import org.springframework.content.rest.StoreRestResource;
import org.springframework.stereotype.Repository;

@Repository
@StoreRestResource(path="files")
public interface FileContentStore extends Store<String> {
}
