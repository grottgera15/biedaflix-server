package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.authority.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoleRepository extends MongoRepository<Role,String> {
    List<Role> findAllById (List<String> ids);
}
