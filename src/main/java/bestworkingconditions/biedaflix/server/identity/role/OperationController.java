package bestworkingconditions.biedaflix.server.identity.role;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/operations")
public class OperationController {

    @GetMapping("")
    @PreAuthorize("hasAuthority('OP_ADMINISTRATE_USERS')")
    public ResponseEntity<?> getOperations(){
        OperationType[] types = OperationType.class.getEnumConstants();
        return ResponseEntity.ok(types);
    }
}
