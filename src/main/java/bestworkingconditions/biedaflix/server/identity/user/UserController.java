package bestworkingconditions.biedaflix.server.identity.user;

import bestworkingconditions.biedaflix.server.common.controller.GenericController;
import bestworkingconditions.biedaflix.server.identity.user.model.User;
import bestworkingconditions.biedaflix.server.identity.user.model.UserRequest;
import bestworkingconditions.biedaflix.server.identity.user.model.UserResponse;
import bestworkingconditions.biedaflix.server.common.util.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController extends GenericController<User,UserResponse,UserService> {

    private final UserRepository repository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService service, UserRepository repository, UserService userService, ModelMapperUtils modelMapperUtils, PasswordEncoder passwordEncoder) {
        super(User.class, UserResponse.class, service,modelMapperUtils);
        this.repository = repository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PatchMapping(value = "/{id}" , consumes = {"application/json","multipart/form-data"})
    @PreAuthorize("authentication.name == #id")
    public ResponseEntity<?> patchUser(
            @PathVariable String id,
            @RequestParam(required = false) @NotBlank Optional<String> username,
            @RequestParam(required = false) @NotBlank Optional<String> email,
            @RequestParam(required = false) @NotBlank Optional<String> password
            ){

        User match = service.findById(id);

        username.ifPresent(match::setUsername);
        email.ifPresent(match::setEmail);
        password.ifPresent(x -> match.setPassword(passwordEncoder.encode(x)));

        repository.save(match);

        return ResponseEntity.ok(new UserResponse(match));

    }

    @PostMapping(consumes = {"application/json","multipart/form-data"})
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserRequest userRequest) {
        User newUser = userService.create(mapper.map(userRequest,User.class));
        return new ResponseEntity<>(mapper.map(newUser,UserResponse.class),HttpStatus.OK);
    }

}
