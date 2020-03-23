package bestworkingconditions.biedaflix.server.identity.user;

import bestworkingconditions.biedaflix.server.identity.user.model.User;
import bestworkingconditions.biedaflix.server.identity.user.model.UserRequest;
import bestworkingconditions.biedaflix.server.identity.user.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User updateUserFromUser(User newInfo, @MappingTarget User target);

    @Mapping(target = "accepted", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User userFromUserRequest(UserRequest request);

    UserResponse userResponseFromUser(User user);
}
