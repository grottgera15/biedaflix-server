package bestworkingconditions.biedaflix.server.identity.role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OperationMapper.class})
public interface RoleMapper {

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "allowedOperations", ignore = true)
    @Mapping(target = "id", source = "id")
    Role roleFromString(String id);
    List<Role> roleFromString(List<String> id);


    RoleDTO roleDtoFromRole(Role role);

    List<RoleDTO> roleDtosFromRole(List<Role> roles);
}
