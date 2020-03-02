package bestworkingconditions.biedaflix.server.model.authority;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Document(collection = "operations")
public class Operation implements GrantedAuthority {

    @Id
    private String id;

    @NotBlank
    private String name;

    public Operation() {
    }

    public Operation(@NotBlank String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
