package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.authority.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role,String> {
    List<Role> findAllById (List<String> ids);
    Optional<Role> findByName(String name);
}
