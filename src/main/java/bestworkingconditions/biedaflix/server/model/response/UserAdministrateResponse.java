package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.User;
import bestworkingconditions.biedaflix.server.model.request.UserAdministrateRequest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserAdministrateResponse extends  UserResponse implements Serializable {

    private List<String> roles = new ArrayList<>();
    private  boolean accepted;

    public UserAdministrateResponse() {
    }

    public UserAdministrateResponse(String id, String email, String username, List<String> roles, boolean accepted) {
        super(id, email, username);
        this.roles = roles;
        this.accepted = accepted;
    }

    public UserAdministrateResponse(String id,UserAdministrateRequest request){
        super(id,request.getEmail(),request.getUsername());
        this.roles = request.getRoles();
        this.accepted = request.isAccepted();
    }

    public UserAdministrateResponse(User user){
        super(user.getId(),user.getEmail(),user.getUsername());
        this.roles = user.getRoles();
        this.accepted = user.isAccepted();
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
