package bestworkingconditions.biedaflix.server.identity.user.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserAdministrateRequest {

    private List<String> roles = new ArrayList<>();
    private  boolean accepted;


    public UserAdministrateRequest(List<String> roles, boolean accepted) {
        this.roles = roles;
        this.accepted = accepted;
    }
}
