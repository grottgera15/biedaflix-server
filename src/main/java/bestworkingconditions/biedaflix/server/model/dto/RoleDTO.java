package bestworkingconditions.biedaflix.server.model.dto;

import bestworkingconditions.biedaflix.server.model.authority.Operation;
import bestworkingconditions.biedaflix.server.model.authority.OperationType;
import bestworkingconditions.biedaflix.server.model.authority.Role;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class RoleDTO {

    @NotBlank
    private String name;

    private List<OperationType> allowedOperations = new ArrayList<>();

    public RoleDTO() {
    }

    public RoleDTO(@NotBlank String name, List<OperationType> allowedOperations) {
        this.name = name;
        this.allowedOperations = allowedOperations;
    }

    public RoleDTO(Role role){
        this.name = role.getName();

        for(GrantedAuthority grantedAuthority : role.getAllowedOperations()){

            this.allowedOperations.add(OperationType.valueOf(grantedAuthority.getAuthority()));
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OperationType> getAllowedOperations() {
        return allowedOperations;
    }

    public void setAllowedOperations(List<OperationType> allowedOperations) {
        this.allowedOperations = allowedOperations;
    }
}
