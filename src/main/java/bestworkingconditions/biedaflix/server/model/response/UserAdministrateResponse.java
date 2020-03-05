package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.User;
import bestworkingconditions.biedaflix.server.model.dto.RoleDTO;
import bestworkingconditions.biedaflix.server.model.request.UserAdministrateRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserAdministrateResponse extends  UserResponse implements Serializable {

    private List<RoleDTO> roles = new ArrayList<>();
    private  boolean accepted;

    public UserAdministrateResponse() {
    }

    public UserAdministrateResponse(String id, String email, String username, List<RoleDTO> roles, boolean accepted) {
        super(id, email, username);
        this.roles = roles;
        this.accepted = accepted;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
