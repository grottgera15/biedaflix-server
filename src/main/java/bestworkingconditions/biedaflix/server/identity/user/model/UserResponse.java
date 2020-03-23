package bestworkingconditions.biedaflix.server.identity.user.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UserResponse implements Serializable {

    private String id;
    private String email;
    private String username;
}
