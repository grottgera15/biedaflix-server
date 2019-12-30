package bestworkingconditions.biedaflix.server.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    public ObjectId id;

    public String email;
    public String password;
    public boolean isValid;

    public User() {
    }

    public User(String email, String password, boolean isValid) {
        this.email = email;
        this.password = password;
        this.isValid = isValid;
    }
}
