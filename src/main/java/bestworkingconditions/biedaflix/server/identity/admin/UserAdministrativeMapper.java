package bestworkingconditions.biedaflix.server.identity.admin;

import bestworkingconditions.biedaflix.server.identity.admin.model.UserAdministrateResponse;
import bestworkingconditions.biedaflix.server.identity.role.OperationMapper;
import bestworkingconditions.biedaflix.server.identity.role.RoleMapper;
import bestworkingconditions.biedaflix.server.identity.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {OperationMapper.class, RoleMapper.class})
public interface UserAdministrativeMapper {

    UserAdministrateResponse userAdministrateResponseFromUser(User user);

}
