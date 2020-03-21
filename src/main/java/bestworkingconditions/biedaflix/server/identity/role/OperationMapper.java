package bestworkingconditions.biedaflix.server.identity.role;

import org.mapstruct.Mapper;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    default String operationTypeFromGrantedAuthority(GrantedAuthority grantedAuthority){
        return grantedAuthority.getAuthority();
    }

    List<String> operationTypeFromOperation(List<Operation> operations);
}
