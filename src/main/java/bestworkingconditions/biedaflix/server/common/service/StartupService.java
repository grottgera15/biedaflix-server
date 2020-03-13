package bestworkingconditions.biedaflix.server.common.service;

import bestworkingconditions.biedaflix.server.user.model.User;
import bestworkingconditions.biedaflix.server.common.model.authority.Operation;
import bestworkingconditions.biedaflix.server.common.model.authority.OperationType;
import bestworkingconditions.biedaflix.server.role.Role;
import bestworkingconditions.biedaflix.server.common.properties.AppProperties;
import bestworkingconditions.biedaflix.server.role.RoleRepository;
import bestworkingconditions.biedaflix.server.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StartupService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AppProperties appProperties;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StartupService(UserRepository userRepository, RoleRepository roleRepository, AppProperties appProperties, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.appProperties = appProperties;
        this.passwordEncoder = passwordEncoder;
    }

    private List<Operation> getAllOperations(){

        List<Operation> allowedOperations = new ArrayList<>();

        for(OperationType type :OperationType.values()){
            allowedOperations.add(new Operation(type));
        }

        return allowedOperations;
    }

    private Role createOwnerRole(){
        Optional<Role> ownerRole = roleRepository.findByName(appProperties.getOwnerRoleName());

        if(ownerRole.isPresent()){
            ownerRole.get().setAllowedOperations(getAllOperations());
            roleRepository.save(ownerRole.get());
            return ownerRole.get();
        }else{
            Role newOwnerRole = new Role(appProperties.getOwnerRoleName(),getAllOperations());
            return roleRepository.save(newOwnerRole);
        }
    }

    private void createUserRole(Role ownerRole){

        Optional<User> ownerUser = userRepository.findByUsername(appProperties.getOwnerUsername());

        if(ownerUser.isPresent()){
            ownerUser.get().setRoles(Collections.singletonList(ownerRole.getId()));
            ownerUser.get().setAccepted(true);
            userRepository.save(ownerUser.get());
        }else {
            User newOwnerUser = new User();
            newOwnerUser.setUsername(appProperties.getOwnerUsername());
            newOwnerUser.setRoles(Collections.singletonList(ownerRole.getId()));
            newOwnerUser.setPassword(passwordEncoder.encode("admin"));
            newOwnerUser.setAccepted(true);
            newOwnerUser.setEmail("example@example.com");

            userRepository.save(newOwnerUser);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void performStartupCheck(){

        Role ownerRole = createOwnerRole();
        createUserRole(ownerRole);

    }

}
