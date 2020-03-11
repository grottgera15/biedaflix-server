package bestworkingconditions.biedaflix.server.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
