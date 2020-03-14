package bestworkingconditions.biedaflix.server.identity.user.model;

import bestworkingconditions.biedaflix.server.common.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Getter @Setter @NoArgsConstructor
public class User extends BaseEntity {

    private String refreshToken;

    @Indexed(unique = true)
    private String email;
    @Indexed(unique = true)
    private String username;

    private String password;
    private Boolean accepted;

    private List<String> roles = new ArrayList<>();

    public User(String id, String refreshToken, String email, String username, String password, Boolean accepted, List<String> roles) {
        super(id);
        this.refreshToken = refreshToken;
        this.email = email;
        this.username = username;
        this.password = password;
        this.accepted = accepted;
        this.roles = roles;
    }

    public User(UserRequest registerRequest){
        this.email = registerRequest.getEmail();
        this.username = registerRequest.getUsername();
        this.password = registerRequest.getPassword();
    }

}
