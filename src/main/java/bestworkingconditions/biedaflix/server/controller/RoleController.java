package bestworkingconditions.biedaflix.server.controller;

import bestworkingconditions.biedaflix.server.model.User;
import bestworkingconditions.biedaflix.server.model.authority.Operation;
import bestworkingconditions.biedaflix.server.model.authority.OperationType;
import bestworkingconditions.biedaflix.server.model.authority.Role;
import bestworkingconditions.biedaflix.server.model.dto.RoleDTO;
import bestworkingconditions.biedaflix.server.repository.RoleRepository;
import bestworkingconditions.biedaflix.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/role")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> addRole(@Valid @RequestBody RoleDTO roleDTO){
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

    @PutMapping("/role")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> updateRole(@NotBlank @RequestParam String id,@Valid @RequestBody RoleDTO roleDTO){
        Role r = createNewRoleFromDTO(roleDTO);
        r.setId(id);
        roleRepository.save(r);

        return ResponseEntity.ok(new RoleDTO(r));
    }

    @DeleteMapping("/role")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> deleteRole(@NotBlank @RequestParam String id){
        List<User> usersContainingRole =  userRepository.findAllByRolesContaining(id);

        for(User u : usersContainingRole){
            u.getRoles().removeIf(id::equals);
        }
        userRepository.saveAll(usersContainingRole);
        roleRepository.deleteById(id);

        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
