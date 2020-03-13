package bestworkingconditions.biedaflix.server.role;

import bestworkingconditions.biedaflix.server.user.model.User;
import bestworkingconditions.biedaflix.server.model.authority.Operation;
import bestworkingconditions.biedaflix.server.model.authority.OperationType;
import bestworkingconditions.biedaflix.server.role.Role;
import bestworkingconditions.biedaflix.server.role.RoleDTO;
import bestworkingconditions.biedaflix.server.role.RoleRepository;
import bestworkingconditions.biedaflix.server.user.UserRepository;
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
public class RoleController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository, UserRepository userRepository) {this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    private Role createNewRoleFromDTO(RoleDTO roleDTO){
        List<Operation> allowedOperations = new ArrayList<>();

        for(OperationType ot : roleDTO.getAllowedOperations()){
            allowedOperations.add(new Operation(ot));
        }

        Role newRole = new Role(roleDTO.getName(),allowedOperations);

        return newRole;
    }

    @GetMapping("/operations")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> getOperations(){

        OperationType[] types = OperationType.class.getEnumConstants();
        return ResponseEntity.ok(types);
    }

    @PostMapping(value = "/roles", consumes = {"application/json"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> addRole(@Valid @RequestBody RoleDTO roleDTO){

        Optional<Role> match = roleRepository.findByName(roleDTO.getName());

        if(match.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name already taken!");
        }

        roleRepository.save(createNewRoleFromDTO(roleDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> getAllRoles(){
        List<Role> roles = roleRepository.findAll();

        List<RoleDTO> roleDTOS = new ArrayList<>();

        roles.forEach(x -> roleDTOS.add(new RoleDTO(x)) );

        return ResponseEntity.ok(roleDTOS);
    }

    @PutMapping(value = "/roles/{id}", consumes = {"application/json"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> updateRole(@PathVariable String id,@Valid @RequestBody RoleDTO roleDTO){
        Role r = createNewRoleFromDTO(roleDTO);

        Optional<Role> match = roleRepository.findByName(roleDTO.getName());

        if( !match.isPresent() | (match.isPresent() && match.get().getId().equals(id))){
            roleRepository.save(r);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "name already taken!");
        }

        return ResponseEntity.ok(new RoleDTO(r));
    }

    @DeleteMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> deleteRole(@PathVariable String id){
        List<User> usersContainingRole =  userRepository.findAllByRolesContaining(id);

        for(User u : usersContainingRole){
            u.getRoles().removeIf(id::equals);
        }
        userRepository.saveAll(usersContainingRole);
        roleRepository.deleteById(id);

        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
