package bestworkingconditions.biedaflix.server.identity.admin;

import bestworkingconditions.biedaflix.server.identity.admin.model.UserAdministrateRequest;
import bestworkingconditions.biedaflix.server.identity.admin.model.UserAdministrateResponse;
import bestworkingconditions.biedaflix.server.identity.role.OperationMapper;
import bestworkingconditions.biedaflix.server.identity.role.RoleMapper;
import bestworkingconditions.biedaflix.server.identity.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {OperationMapper.class, RoleMapper.class})
public interface UserAdministrativeMapper {

    UserAdministrateResponse userAdministrateResponseFromUser(User user);

    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "username", ignore = true)
    User userFromUserAdministrativeRequest(UserAdministrateRequest request);
}
