package bestworkingconditions.biedaflix.server.identity.admin;

import bestworkingconditions.biedaflix.server.common.PageMapper;
import bestworkingconditions.biedaflix.server.common.model.PageDTO;
import bestworkingconditions.biedaflix.server.identity.admin.model.UserAdministrateRequest;
import bestworkingconditions.biedaflix.server.identity.admin.model.UserAdministrateResponse;
import bestworkingconditions.biedaflix.server.identity.user.UserRepository;
import bestworkingconditions.biedaflix.server.identity.user.UserService;
import bestworkingconditions.biedaflix.server.identity.user.model.User;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/admin/users")
public class AdminController {

    private final UserRepository repository;
    private final UserService userService;
    private final UserAdministrativeMapper userAdministrativeMapper;
    private final PageMapper pageMapper;

    @Autowired
    public AdminController(UserRepository repository, UserService userService, UserAdministrativeMapper userAdministrativeMapper, PageMapper pageMapper) {
        this.repository = repository;
        this.userService = userService;
        this.userAdministrativeMapper = userAdministrativeMapper;
        this.pageMapper = pageMapper;
    }


    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> updateUser(@PathVariable String id,@Valid @RequestBody UserAdministrateRequest administrateRequest){
        User user = userService.fetchAndUpdate(id,userAdministrativeMapper.userFromUserAdministrativeRequest(administrateRequest));
        return ResponseEntity.ok(userAdministrativeMapper.userAdministrateResponseFromUser(user));
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> deleteUser(
            @PathVariable String id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value= "/admin/users/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> GetSingleAdministrativeUser(@PathVariable String id){
        return ResponseEntity.ok(userAdministrativeMapper.userAdministrateResponseFromUser(userService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<?> getUsers(
            HttpServletRequest request,
            @QuerydslPredicate(root = User.class) Predicate predicate,
            Pageable pageable
    ){

        //PageDTO<UserAdministrateResponse> responsePageDTO =
         //       pageMapper.pageDTOFromPage(repository.findAll(predicate,pageable),userAdministrativeMapper::userAdministrateResponseFromUser,request);

        //return ResponseEntity.ok(responsePageDTO);
        return null;
    }

    /*
    //TODO: QueryDSL and pagination
    @GetMapping(value = "")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false) Optional<String> roleId,
            @RequestParam(required = false) Optional<Boolean> accepted
    ) {

        User example = new User();
        example.setAccepted(null);
        example.setRoles(null);

        roleId.ifPresent(s -> example.setRoles(Collections.singletonList(s)));
        accepted.ifPresent(example::setAccepted);

        List<User> requestedUsers = repository.findAll(Example.of(example));
        List<UserAdministrateResponse> userAdministrateResponses = new ArrayList<>();

        requestedUsers.forEach(x -> userAdministrateResponses.add(userService.CreateUserAdministrateResponseFromUser(x)));
        return ResponseEntity.ok(userAdministrateResponses);
    }

     */
}
