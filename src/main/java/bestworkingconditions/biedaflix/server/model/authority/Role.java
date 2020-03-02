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

    @Id
    private String id;

    @NotBlank
    private String name;
    private List<Operation> allowedOperations = new ArrayList<>();

    @Override
    public String getAuthority() {
        return name;
    }

    public void addOperation(Operation op){
        allowedOperations.add(op);
    }

    public Collection<? extends GrantedAuthority> getAllowedOperations(){
        return allowedOperations;
    }
}
