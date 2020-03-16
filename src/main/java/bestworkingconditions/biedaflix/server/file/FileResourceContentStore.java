package bestworkingconditions.biedaflix.server.file;

import org.springframework.content.commons.repository.ContentStore;
import org.springframework.content.rest.StoreRestResource;

@StoreRestResource(path = "/fileResource")
public interface FileResourceContentStore extends ContentStore<FileResource,String> {
}
