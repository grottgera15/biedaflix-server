package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.User;
import bestworkingconditions.biedaflix.server.model.request.UserRequest;
import bestworkingconditions.biedaflix.server.model.response.UserResponse;
import bestworkingconditions.biedaflix.server.repository.UserRepository;
import bestworkingconditions.biedaflix.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin()
public class UserController {

    private final UserRepository repository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository repository, UserService userService, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PatchMapping(value = "/users/{id}" , consumes = {"multipart/form-data"})
    @PreAuthorize("authentication.name == #id")
    public ResponseEntity<?> patchUser(
            @PathVariable String id,
            @RequestParam(required = false) Optional<String> username,
            @RequestParam(required = false) Optional<String> email,
            @RequestParam(required = false) Optional<String> password
            ){

        Optional<User> match = repository.findById(id);

        if(match.isPresent()){

            User u = match.get();

            username.ifPresent(u::setUsername);
            email.ifPresent(u::setEmail);
            password.ifPresent(x -> u.setPassword(passwordEncoder.encode(x)));

            repository.save(u);

            return ResponseEntity.ok(new UserResponse(u));

        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user of given id does not exist!");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(
    ){
        List<UserResponse> response = new ArrayList<>();

        repository.findAll().forEach(x -> response.add(new UserResponse(x)));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(
            @PathVariable String id){
        return ResponseEntity.ok(new UserResponse(repository.findById(id).orElseThrow( ()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"user of given id does not exist!"))));
    }

    @PostMapping(value = "/users", consumes = {"application/json"})
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserRequest userRequest) {

        List<User> repositoryAll = repository.findAll();

        if(repositoryAll.stream().anyMatch(t-> t.getEmail().equals(userRequest.getEmail())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user of given email already exists in the database!");

        if(repositoryAll.stream().anyMatch(t-> t.getUsername().equals(userRequest.getUsername())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user of given username already exists in the database!");

        String plainText = userRequest.getPassword();
        userRequest.setPassword(passwordEncoder.encode(plainText));

        User newUser = new User(userRequest);

        newUser.setAccepted(false);
        repository.save(newUser);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
