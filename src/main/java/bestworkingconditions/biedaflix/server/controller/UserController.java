package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.User;
import bestworkingconditions.biedaflix.server.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    public UserController(@Autowired UserRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return repository.findAll();
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") ObjectId id){
        return repository.findUserById(id);
    }

}
