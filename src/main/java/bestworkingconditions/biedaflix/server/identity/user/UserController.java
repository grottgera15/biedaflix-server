package bestworkingconditions.biedaflix.server.identity.user;

import bestworkingconditions.biedaflix.server.identity.user.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "/{id}/avatar",  consumes = {"multipart/form-data"})
    @PreAuthorize("authentication.name == #id")
    public ResponseEntity<?> setAvatar(@PathVariable String id, MultipartFile file){
        return ResponseEntity.ok(userMapper.userResponseFromUser(userService.setAvatar(id,file)));
    }

    @PatchMapping(value = "/{id}" , consumes = {"application/json"})
    @PreAuthorize("authentication.name == #id")
    public ResponseEntity<?> patchUser(@PathVariable String id, @RequestBody UserRequest request){
        return ResponseEntity.ok(userService.fetchAndUpdate(id,userMapper.userFromUserRequest(request)));

    }

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<?> registerNewUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.create(userMapper.userFromUserRequest(userRequest)));
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("authentication.name == #id")
    public ResponseEntity<?> deleteUser(
            @PathVariable String id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
