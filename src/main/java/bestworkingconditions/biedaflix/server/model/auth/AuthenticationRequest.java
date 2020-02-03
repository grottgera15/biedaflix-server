package bestworkingconditions.biedaflix.server.model.auth;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
    private String email;
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
