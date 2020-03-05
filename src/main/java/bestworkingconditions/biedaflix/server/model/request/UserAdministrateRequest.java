package bestworkingconditions.biedaflix.server.model.request;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class UserAdministrateRequest extends UserRegisterRequest {

    private List<String> roles = new ArrayList<>();
    private  boolean accepted;

    public UserAdministrateRequest() {
    }

    public UserAdministrateRequest(@NotBlank String email, @NotBlank String username, @NotBlank String password, List<String> roles, boolean accepted) {
        super(email, username, password);
        this.roles = roles;
        this.accepted = accepted;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
