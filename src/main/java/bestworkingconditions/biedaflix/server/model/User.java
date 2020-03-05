package bestworkingconditions.biedaflix.server.model;

import bestworkingconditions.biedaflix.server.model.request.UserAdministrateRequest;
import bestworkingconditions.biedaflix.server.model.request.UserRegisterRequest;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String refreshToken;

    private String email;
    private String username;
    private String password;
    private boolean accepted;

    private List<String> roles = new ArrayList<>();

    public User() {
    }

    public User(String id, String refreshToken, String email, String username, String password, boolean accepted, List<String> roles) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.email = email;
        this.username = username;
        this.password = password;
        this.accepted = accepted;
        this.roles = roles;
    }

    public User(UserRegisterRequest registerRequest){
        this.email = registerRequest.getEmail();
        this.username = registerRequest.getUsername();
        this.password = registerRequest.getPassword();
    }

    public User(UserAdministrateRequest userAdministrateRequest){
        this((UserRegisterRequest) userAdministrateRequest);
        this.roles = userAdministrateRequest.getRoles();
        this.accepted = userAdministrateRequest.isAccepted();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
