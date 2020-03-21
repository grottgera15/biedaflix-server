package bestworkingconditions.biedaflix.server.identity.auth;

import bestworkingconditions.biedaflix.server.identity.user.model.User;
import bestworkingconditions.biedaflix.server.identity.role.Role;
import bestworkingconditions.biedaflix.server.identity.role.RoleRepository;
import bestworkingconditions.biedaflix.server.identity.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("mongo")
public class MongoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public MongoUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmailOrId(username,username,username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"user does not exist")
        );

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        List<Role> userRoles = (List<Role>) roleRepository.findAllById(user.getRoles());

        for ( Role role : userRoles ){
            grantedAuthorityList.add(role);
            grantedAuthorityList.addAll(role.getAllowedOperations());
        }
        return new org.springframework.security.core.userdetails.User(user.getId(),user.getPassword(), grantedAuthorityList);
    }
}
