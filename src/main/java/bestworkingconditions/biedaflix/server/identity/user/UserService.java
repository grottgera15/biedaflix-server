package bestworkingconditions.biedaflix.server.identity.user;

import bestworkingconditions.biedaflix.server.file.service.FileService;
import bestworkingconditions.biedaflix.server.file.service.GenericFileHandlingServiceImpl;
import bestworkingconditions.biedaflix.server.identity.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Service
public class UserService extends GenericFileHandlingServiceImpl<User, UserRepository> {


    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository repository, FileService fileService, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        super(repository, fileService);
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User setAvatar(String id, MultipartFile file){
        return setFileReference(id,file,User::setAvatar);
    }

    @Override
    public User create(@Valid User resource) {

        resource.setPassword(passwordEncoder.encode(resource.getPassword()));
        resource.setAccepted(false);

        return repository.save(resource);
    }

    @Override
    public User fetchAndUpdate(String id, User resource) {
        User requestedUser = findById(id);

        if(resource.getPassword() != null){
            resource.setPassword(passwordEncoder.encode(resource.getPassword()));
        }

        return update(userMapper.updateUserFromUser(resource,requestedUser));
    }

}
