package bestworkingconditions.biedaflix.server.identity.role;

import bestworkingconditions.biedaflix.server.file.service.FileService;
import bestworkingconditions.biedaflix.server.file.service.GenericFileHandlingServiceImpl;
import bestworkingconditions.biedaflix.server.identity.user.UserRepository;
import bestworkingconditions.biedaflix.server.identity.user.model.User;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class RoleService extends GenericFileHandlingServiceImpl<Role,RoleRepository> {

    private final UserRepository userRepository;

    public RoleService(RoleRepository repository, FileService fileService, UserRepository userRepository) {
        super(repository, fileService);
        this.userRepository = userRepository;
    }

    @Override
    public Role fetchAndUpdate(String id, @Valid Role resource) {
        resource.setId(id);
        return repository.save(resource);
    }

    @Override
    public void deleteById(String id) {
        List<User> usersContainingRole =  userRepository.findAllByRolesContaining(id);

        for(User u : usersContainingRole){
            u.getRoles().removeIf(role -> role.getId().equals(id));
        }

        userRepository.saveAll(usersContainingRole);
        repository.deleteById(id);
    }
}
