package bestworkingconditions.biedaflix.server.user.model;

import bestworkingconditions.biedaflix.server.model.BaseDTO;
import bestworkingconditions.biedaflix.server.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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
