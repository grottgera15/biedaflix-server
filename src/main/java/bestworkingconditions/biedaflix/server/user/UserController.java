package bestworkingconditions.biedaflix.server.user;

import bestworkingconditions.biedaflix.server.controller.GenericController;
import bestworkingconditions.biedaflix.server.user.model.User;
import bestworkingconditions.biedaflix.server.user.model.UserRequest;
import bestworkingconditions.biedaflix.server.user.model.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController extends GenericController<User,UserResponse,UserService> {

    private final UserRepository repository;
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public UserController(UserService service, ModelMapper mapper, UserRepository repository, UserService userService, ModelMapper mapper1) {
        super(User.class, UserResponse.class, service, mapper);
        this.repository = repository;
        this.userService = userService;
        this.mapper = mapper1;
    }

    @PatchMapping(value = "/{id}" , consumes = {"application/json","multipart/form-data"})
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
            //password.ifPresent(x -> u.setPassword(passwordEncoder.encode(x)));

            repository.save(u);

            return ResponseEntity.ok(new UserResponse(u));

        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"user of given id does not exist!");
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getUsers(
    ){
        List<UserResponse> response = new ArrayList<>();

        repository.findAll().forEach(x -> response.add(new UserResponse(x)));

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/",consumes = {"application/json","multipart/form-data"})
    public ResponseEntity<?> registerNewUser(@Valid @RequestParam UserRequest userRequest) {
        return new ResponseEntity<>(userService.create(mapper.map(userRequest,User.class)),HttpStatus.OK);
    }

}
