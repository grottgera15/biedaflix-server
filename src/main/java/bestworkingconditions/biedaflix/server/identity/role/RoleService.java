package bestworkingconditions.biedaflix.server.identity.role;

import bestworkingconditions.biedaflix.server.file.service.FileService;
import bestworkingconditions.biedaflix.server.file.service.GenericFileHandlingServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends GenericFileHandlingServiceImpl<Role,RoleRepository> {

    public RoleService(RoleRepository repository, FileService fileService) {
        super(repository, fileService);
    }

    @Override
    public Role fetchAndUpdate(String id, Role resource) {
        return null;
    }
}
