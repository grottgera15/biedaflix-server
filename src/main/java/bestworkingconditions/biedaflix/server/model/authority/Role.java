package bestworkingconditions.biedaflix.server.model.authority;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "roles")
public class Role implements GrantedAuthority {

    private static final  String prefix = "ROLE_";

    @Id
    private String id;

    @NotBlank
    private String name;
    private List<Operation> allowedOperations = new ArrayList<>();

    public Role() {
    }

    public Role(@NotBlank String name, List<Operation> allowedOperations) {
        this.id = id;
        this.name = name;
        this.allowedOperations = allowedOperations;
    }

    @Override
    public String getAuthority() {
        return prefix + name;
    }

    public void addOperation(Operation op){
        allowedOperations.add(op);
    }

    public Collection<? extends GrantedAuthority> getAllowedOperations(){
        return allowedOperations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAllowedOperations(List<Operation> allowedOperations) {
        this.allowedOperations = allowedOperations;
    }
}
