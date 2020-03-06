package bestworkingconditions.biedaflix.server.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

public class UserAdministrateRequest {

    private List<String> roles = new ArrayList<>();
    private  boolean accepted;

    public UserAdministrateRequest() {
    }

    public UserAdministrateRequest(List<String> roles, boolean accepted) {
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
