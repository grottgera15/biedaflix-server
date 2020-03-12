package bestworkingconditions.biedaflix.server.model;

import bestworkingconditions.biedaflix.server.model.request.UserRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class UserTest {

    private static final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void checkUserRequestMapping(){

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test@gmail.com");
        userRequest.setUsername("testUsername");
        userRequest.setPassword("testPassword");

        User user = modelMapper.map(userRequest,User.class);
        Assert.assertEquals(userRequest.getEmail(),user.getEmail());
        Assert.assertEquals(userRequest.getPassword(),user.getPassword());
        Assert.assertEquals(userRequest.getUsername(),user.getUsername());

    }

}
