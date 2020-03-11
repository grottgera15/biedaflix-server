package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.User;
import bestworkingconditions.biedaflix.server.model.request.UserAdministrateRequest;
import bestworkingconditions.biedaflix.server.model.request.UserRegisterRequest;
import bestworkingconditions.biedaflix.server.model.response.UserAdministrateResponse;
import bestworkingconditions.biedaflix.server.model.response.UserResponse;
import bestworkingconditions.biedaflix.server.repository.UserRepository;
import bestworkingconditions.biedaflix.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
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

    @PutMapping(value = "/admin/users/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserAdministrateRequest administrateRequest, @PathVariable String id){

        Optional<User> match = repository.findById(id);

        if( match.isPresent() && match.get().getId().equals(id)){

            User u = match.get();
            u.setRoles(administrateRequest.getRoles());
            u.setAccepted(administrateRequest.isAccepted());

            u = repository.save(u);
            return ResponseEntity.ok(userService.CreateUserAdministrateResponseFromUser(u));
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"username or email already taken!");
        }
    }

    @DeleteMapping(value = "/admin/users/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> deleteUser(
            @PathVariable String id){
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value= "/admin/users/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> GetSingleAdministrativeUser(
            @PathVariable String id
    ){
        return ResponseEntity.ok(userService.CreateUserAdministrateResponseFromUser(repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"user of given id does not exist!")
        )));
    }

    @GetMapping(value = "/admin/users")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false) Optional<String> roleId,
            @RequestParam(required = false) Optional<Boolean> accepted
    ){

        User example = new User();
        example.setAccepted(null);
        example.setRoles(null);

        roleId.ifPresent(s -> example.setRoles(Collections.singletonList(s)));
        accepted.ifPresent(example::setAccepted);

        List<User> requestedUsers = repository.findAll(Example.of(example));
        List<UserAdministrateResponse> userAdministrateResponses = new ArrayList<>();

        requestedUsers.forEach( x -> userAdministrateResponses.add(userService.CreateUserAdministrateResponseFromUser(x)));
        return ResponseEntity.ok(userAdministrateResponses);
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

    @PostMapping(value = "/users", consumes = {"multipart/form-data"})
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserRegisterRequest userRequest) {

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
