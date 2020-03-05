package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.StreamingServiceSource;
import bestworkingconditions.biedaflix.server.model.User;
import bestworkingconditions.biedaflix.server.model.request.UserAdministrateRequest;
import bestworkingconditions.biedaflix.server.model.request.UserRegisterRequest;
import bestworkingconditions.biedaflix.server.model.response.UserAdministrateResponse;
import bestworkingconditions.biedaflix.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

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

    @PutMapping(value = "/administrateUser")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> updateUser(@RequestParam @NotBlank String id, @Valid @RequestBody UserAdministrateRequest administrateRequest){

        Optional<User> match = repository.findByUsernameOrEmail(administrateRequest.getUsername(),administrateRequest.getEmail());

        if(!match.isPresent() | ( match.isPresent() && match.get().getId().equals(id)) ){

            administrateRequest.setPassword(passwordEncoder.encode(administrateRequest.getPassword()));
            User newUserData = new User(administrateRequest);
            repository.save(newUserData);
            return ResponseEntity.ok(new UserAdministrateResponse(newUserData));
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"username or email already taken!");
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserRegisterRequest userRequest) {

        List<User> repositoryAll = repository.findAll();

        if(repositoryAll.stream().anyMatch(t-> t.getEmail().equals(userRequest.getEmail())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user of given email already exists in the database!");

        if(repositoryAll.stream().anyMatch(t-> t.getUsername().equals(userRequest.getUsername())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user of given email already exists in the database!");

        String plainText = userRequest.getPassword();
        userRequest.setPassword(passwordEncoder.encode(plainText));

        User newUser = new User(userRequest);

        newUser.setAccepted(false);
        repository.save(newUser);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
