package bestworkingconditions.biedaflix.server.model.authority;

import org.springframework.security.core.GrantedAuthority;

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
