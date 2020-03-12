package bestworkingconditions.biedaflix.server.model;

import bestworkingconditions.biedaflix.server.model.request.UserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Getter @Setter @NoArgsConstructor
public class User {

    @Id
    private String id;
    private String refreshToken;

    @Indexed(unique = true)
    private String email;
    @Indexed(unique = true)
    private String username;

    private String password;
    private Boolean accepted;

    private List<String> roles = new ArrayList<>();

    public User(String id, String refreshToken, String email, String username, String password, boolean accepted, List<String> roles) {
        this.id = id;
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
