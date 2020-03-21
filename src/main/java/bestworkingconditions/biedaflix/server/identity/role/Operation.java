package bestworkingconditions.biedaflix.server.identity.role;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
public class Operation implements GrantedAuthority {

    private OperationType type;

    public Operation(OperationType type) {
        this.type = type;
    }

    @Override
    public String getAuthority() {
        return type.getOperationName();
    }

    public OperationType getType() {
        return type;
    }

}
