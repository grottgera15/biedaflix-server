package bestworkingconditions.biedaflix.server.identity.admin.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserAdministrateRequest {

    private List<String> roles = new ArrayList<>();
    private  boolean accepted;
}
