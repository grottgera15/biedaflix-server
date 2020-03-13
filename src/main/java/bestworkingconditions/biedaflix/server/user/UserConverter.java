package bestworkingconditions.biedaflix.server.user;

import bestworkingconditions.biedaflix.server.service.GenericConverter;
import bestworkingconditions.biedaflix.server.user.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends GenericConverter<User> {

    public UserConverter(ModelMapper mapper) {
        super(mapper);
    }


}
