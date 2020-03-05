package bestworkingconditions.biedaflix.server.model.dto;

import bestworkingconditions.biedaflix.server.model.authority.Operation;
import bestworkingconditions.biedaflix.server.model.authority.OperationType;
import bestworkingconditions.biedaflix.server.model.authority.Role;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoleDTO implements Serializable {

    private String id;

    @NotBlank
    private String name;

    private List<OperationType> allowedOperations = new ArrayList<>();

    public RoleDTO() {
    }

    public RoleDTO(String id, @NotBlank String name, List<OperationType> allowedOperations) {
        this.id = id;
        this.name = name;
        this.allowedOperations = allowedOperations;
    }

    public RoleDTO(Role role){
        this.name = role.getName();
        this.id = role.getId();

        for(Operation op : role.getOperationEnumList()){
            this.allowedOperations.add(OperationType.valueOf(op.getType().toString()));
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
