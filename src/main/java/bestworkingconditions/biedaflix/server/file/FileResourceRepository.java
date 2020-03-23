package bestworkingconditions.biedaflix.server.file;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileResourceRepository  extends MongoRepository<FileResource,String> {
}
