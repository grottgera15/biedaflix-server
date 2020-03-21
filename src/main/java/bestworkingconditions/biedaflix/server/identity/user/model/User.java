package bestworkingconditions.biedaflix.server.identity.user.model;

import bestworkingconditions.biedaflix.server.file.FileResource;
import bestworkingconditions.biedaflix.server.identity.role.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String refreshToken;

    @Indexed(unique = true)
    private String email;
    @Indexed(unique = true)
    private String username;

    private String password;
    private Boolean accepted;

    @DBRef
    private List<Role> roles = new ArrayList<>();

    private FileResource avatar;
}
