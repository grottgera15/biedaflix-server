package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.StreamingServiceSource;
import bestworkingconditions.biedaflix.server.model.User;
import bestworkingconditions.biedaflix.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin()
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody User user) {

        List<User> repositoryAll = repository.findAll();

        if(repositoryAll.stream().anyMatch(t-> t.getEmail().equals(user.email)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user of given email already exists in the database!");

        String plainText = user.getPassword();
        user.setPassword(passwordEncoder.encode(plainText));

        user.setAccepted(false);
        repository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
