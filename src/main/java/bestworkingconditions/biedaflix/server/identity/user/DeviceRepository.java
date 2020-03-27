package bestworkingconditions.biedaflix.server.identity.user;

import bestworkingconditions.biedaflix.server.identity.user.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceRepository extends MongoRepository<Device,String>  {
    List<Device> findByUserId(String userId);
}
