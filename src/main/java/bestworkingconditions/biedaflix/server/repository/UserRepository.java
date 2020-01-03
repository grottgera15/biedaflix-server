package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {
    User findUserById(String id);
    User findUserByEmail(String email);
    List<User> findAll();
}
