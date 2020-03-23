package bestworkingconditions.biedaflix.server.identity.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleController(RoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @PostMapping(consumes = {"application/json"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> addRole(@Valid @RequestBody RoleDTO roleDTO){
        roleService.create(roleMapper.roleFromRoleDTO(roleDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"})
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> updateRole(@PathVariable String id,@Valid @RequestBody RoleDTO roleDTO){
        return ResponseEntity.ok(roleService.fetchAndUpdate(id,roleMapper.roleFromRoleDTO(roleDTO)));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> getAllRoles(){
        return ResponseEntity.ok(roleMapper.roleDtosFromRole(roleService.findAll()));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> deleteRole(@PathVariable String id){
        roleService.deleteById(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
