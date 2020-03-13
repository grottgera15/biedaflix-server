package bestworkingconditions.biedaflix.server.identity.user.model;

import bestworkingconditions.biedaflix.server.common.model.BaseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse extends BaseDTO {

    private String id;
    private String email;
    private String username;

    public UserResponse(String id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public UserResponse(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }
}
