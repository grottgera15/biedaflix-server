package bestworkingconditions.biedaflix.server.model;

import bestworkingconditions.biedaflix.server.model.authority.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    public String id;

    public String email;
    public String password;
    public boolean accepted;

    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(String id, String email, String password, boolean accepted, List<Role> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.accepted = accepted;
        this.roles = roles;
    }

    public String getId() {
        return id;
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

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
