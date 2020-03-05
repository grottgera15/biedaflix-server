package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.DeviceMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceMetadataRepository extends MongoRepository<DeviceMetadata,String> {
    List<DeviceMetadata> findByUserId(String userId);
}
