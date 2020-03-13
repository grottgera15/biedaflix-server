package bestworkingconditions.biedaflix.server.user.model;

import bestworkingconditions.biedaflix.server.role.RoleDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserAdministrateResponse extends UserResponse implements Serializable {

    private List<RoleDTO> roles = new ArrayList<>();
    private  boolean accepted;

    public UserAdministrateResponse(String id, String email, String username, List<RoleDTO> roles, boolean accepted) {
        super(id, email, username);
        this.roles = roles;
        this.accepted = accepted;
    }
}
