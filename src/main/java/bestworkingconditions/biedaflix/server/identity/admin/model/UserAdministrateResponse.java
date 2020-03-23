package bestworkingconditions.biedaflix.server.identity.admin.model;

import bestworkingconditions.biedaflix.server.identity.role.RoleDTO;
import bestworkingconditions.biedaflix.server.identity.user.model.UserResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserAdministrateResponse extends UserResponse {

    private List<RoleDTO> roles = new ArrayList<>();
    private  boolean accepted;
}
