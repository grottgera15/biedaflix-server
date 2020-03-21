package bestworkingconditions.biedaflix.server.identity.user;

import bestworkingconditions.biedaflix.server.identity.user.model.User;
import bestworkingconditions.biedaflix.server.identity.user.model.UserRequest;
import bestworkingconditions.biedaflix.server.identity.user.model.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {


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

        throw new NotImplementedException();

        //User newUser = userService.create(mapper.map(userRequest,User.class));
        //return new ResponseEntity<>(mapper.map(newUser,UserResponse.class),HttpStatus.OK);
    }

}
