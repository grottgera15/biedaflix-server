package bestworkingconditions.biedaflix.server.role;

import bestworkingconditions.biedaflix.server.common.model.authority.Operation;
import bestworkingconditions.biedaflix.server.common.model.authority.OperationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoleDTO implements Serializable {

    private String id;

    @NotBlank
    private String name;

    private List<OperationType> allowedOperations = new ArrayList<>();

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
}
