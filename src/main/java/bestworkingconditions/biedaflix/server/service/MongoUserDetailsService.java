package bestworkingconditions.biedaflix.server.service;

import bestworkingconditions.biedaflix.server.model.User;
import bestworkingconditions.biedaflix.server.model.authority.Operation;
import bestworkingconditions.biedaflix.server.model.authority.Role;
import bestworkingconditions.biedaflix.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("mongo")
public class MongoUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MongoUserDetailsService(UserRepository userRepository) {this.userRepository = userRepository;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        for ( Role role : user.getRoles() ){
            grantedAuthorityList.add(role);
            grantedAuthorityList.addAll(role.getAllowedOperations());
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), grantedAuthorityList);
    }
}
