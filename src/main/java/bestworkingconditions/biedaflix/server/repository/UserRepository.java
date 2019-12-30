package bestworkingconditions.biedaflix.server.repository;

import bestworkingconditions.biedaflix.server.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {
    User findUserById(ObjectId id);
    List<User> findAll();
}
