package bestworkingconditions.biedaflix.server.user;

import bestworkingconditions.biedaflix.server.model.authority.Role;
import bestworkingconditions.biedaflix.server.model.dto.RoleDTO;
import bestworkingconditions.biedaflix.server.repository.RoleRepository;
import bestworkingconditions.biedaflix.server.service.GenericServiceImpl;
import bestworkingconditions.biedaflix.server.user.UserRepository;
import bestworkingconditions.biedaflix.server.user.model.User;
import bestworkingconditions.biedaflix.server.user.model.UserAdministrateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends GenericServiceImpl<User, UserRepository> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository repository, UserRepository userRepository, RoleRepository roleRepository) {
        super(repository);
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserAdministrateResponse CreateUserAdministrateResponseFromUser(User u){

        List<Role> userRoles = (List<Role>) roleRepository.findAllById(u.getRoles());
        List<RoleDTO> userRolesDTOList = new ArrayList<>();

        userRoles.forEach(x -> userRolesDTOList.add(new RoleDTO(x)));

        User found = findById(u.getId());

        return new UserAdministrateResponse(
                u.getId(),
                u.getEmail(),
                u.getUsername(),
                userRolesDTOList,
                u.getAccepted()
        );
    }


    @Override
    public User create(User resource) {
        return null;
    }

    @Override
    public User update(User resource) {
        return null;
    }
}
