package bestworkingconditions.biedaflix.server.service;

import bestworkingconditions.biedaflix.server.model.User;
import bestworkingconditions.biedaflix.server.model.authority.Role;
import bestworkingconditions.biedaflix.server.model.dto.RoleDTO;
import bestworkingconditions.biedaflix.server.model.response.UserAdministrateResponse;
import bestworkingconditions.biedaflix.server.repository.RoleRepository;
import bestworkingconditions.biedaflix.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private  final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }



    public UserAdministrateResponse CreateUserAdministrateResponseFromUser(User u){

        List<Role> userRoles =  roleRepository.findAllById(u.getRoles());
        List<RoleDTO> userRolesDTOList = new ArrayList<>();

        userRoles.forEach(x -> userRolesDTOList.add(new RoleDTO(x)));

        return new UserAdministrateResponse(
                u.getId(),
                u.getEmail(),
                u.getUsername(),
                userRolesDTOList,
                u.isAccepted()
        );
    }
}