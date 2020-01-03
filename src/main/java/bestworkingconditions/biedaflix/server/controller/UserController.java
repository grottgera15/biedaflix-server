package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.User;
import bestworkingconditions.biedaflix.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    public UserController(@Autowired UserRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/")
    public List<User> getAllUsers(){
        return repository.findAll();
    }

    @RequestMapping(value="/{id}")
    public User getUserById(@PathVariable("id") String id){
        return repository.findUserById(id);
    }

    @PostMapping(value = "/")
    public User createUser(@Valid @RequestBody User user){
        repository.save(user);
        return user;
    }

}
