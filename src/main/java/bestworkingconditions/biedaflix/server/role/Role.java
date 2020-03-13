package bestworkingconditions.biedaflix.server.role;

import bestworkingconditions.biedaflix.server.model.authority.Operation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role implements GrantedAuthority {

    private static final  String prefix = "ROLE_";

    @Id
    private String id;

    @NotBlank
    private String name;
    private List<Operation> allowedOperations = new ArrayList<>();

    public Role(@NotBlank String name, List<Operation> allowedOperations) {
        this.name = name;
        this.allowedOperations = allowedOperations;
    }

    public Role(String id, @NotBlank String name, List<Operation> allowedOperations) {
        this.id = id;
        this.name = name;
        this.allowedOperations = allowedOperations;
    }

    @Override
    public String getAuthority() {
        return prefix + name;
    }

    public Collection<? extends GrantedAuthority> getAllowedOperations(){
        return allowedOperations;
    }

    public List<Operation> getOperationEnumList(){
        return allowedOperations;
    }

}
