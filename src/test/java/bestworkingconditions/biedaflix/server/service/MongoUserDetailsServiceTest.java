package bestworkingconditions.biedaflix.server.service;

import bestworkingconditions.biedaflix.server.model.User;
import bestworkingconditions.biedaflix.server.model.authority.Operation;
import bestworkingconditions.biedaflix.server.model.authority.Role;
import bestworkingconditions.biedaflix.server.repository.UserRepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

class MongoUserDetailsServiceTest {
/*
    private MongoUserDetailsService mongoUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeAll
    void setUp() {
        mongoUserDetailsService = new MongoUserDetailsService(userRepository);
    }

    @Test
    void loadUserByUsername() {

        User testUser = new User();
        testUser.setAccepted(true);
        testUser.setEmail("test");
        testUser.setPassword("1234");

        Role admin = new Role();
        admin.addOperation(new Operation("OP_DELETE"));
        admin.addOperation(new Operation("OP_WRITE"));

        Role user = new Role();
        user.addOperation(new Operation("OP_READ"));

        List<Role> roles = new ArrayList<>();

        testUser.setRoles(roles);

        when(userRepository.findUserByEmail("test")).thenReturn(testUser);


    }

 */
}