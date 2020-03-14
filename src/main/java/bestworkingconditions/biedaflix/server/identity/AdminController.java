package bestworkingconditions.biedaflix.server.identity;

import bestworkingconditions.biedaflix.server.identity.user.model.User;
import bestworkingconditions.biedaflix.server.identity.user.model.UserAdministrateRequest;
import bestworkingconditions.biedaflix.server.identity.user.model.UserAdministrateResponse;
import bestworkingconditions.biedaflix.server.identity.user.UserRepository;
import bestworkingconditions.biedaflix.server.identity.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class AdminController {

    private final UserRepository repository;
    private final UserService userService;

    @Autowired
    public AdminController(UserRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
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

}
