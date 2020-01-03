package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.User;
import bestworkingconditions.biedaflix.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "/")
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @RequestMapping(value = "/{id}")
    public User getUserById(@PathVariable("id") String id) {
        return repository.findUserById(id);
    }

    @PostMapping(value = "/")
    public User registerNewUser(@Valid @RequestBody User user) {

        String plainText = user.getPassword();
        user.setPassword(passwordEncoder.encode(plainText));

        user.setAccepted(false);
        repository.save(user);
        return user;
    }

}
