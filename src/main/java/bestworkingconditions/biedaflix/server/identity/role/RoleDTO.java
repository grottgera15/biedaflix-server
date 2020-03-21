package bestworkingconditions.biedaflix.server.identity.role;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class RoleDTO implements Serializable {

    private String id;

    @NotBlank
    private String name;

    private List<String> allowedOperations = new ArrayList<>();
}
