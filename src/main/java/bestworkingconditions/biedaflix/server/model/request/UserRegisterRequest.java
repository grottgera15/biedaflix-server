package bestworkingconditions.biedaflix.server.model.request;

import javax.validation.constraints.NotBlank;

public class UserRegisterRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public UserRegisterRequest() {
    }

    public UserRegisterRequest(@NotBlank String email, @NotBlank String username, @NotBlank String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
