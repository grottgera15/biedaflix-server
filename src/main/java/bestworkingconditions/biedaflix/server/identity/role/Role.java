package bestworkingconditions.biedaflix.server.identity.role;

import lombok.Data;
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
@Data
@NoArgsConstructor
public class Role implements GrantedAuthority {

    private static final  String prefix = "ROLE_";

    @Id
    private String id;

    @NotBlank
    private String name;
    private List<Operation> allowedOperations = new ArrayList<>();

    @Override
    public String getAuthority() {
        return prefix + name;
    }

    public Collection<? extends GrantedAuthority> getAllowedOperations(){
        return allowedOperations;
    }
}
