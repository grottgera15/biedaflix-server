package bestworkingconditions.biedaflix.server.model.response;

import bestworkingconditions.biedaflix.server.model.User;

import java.io.Serializable;

public class UserResponse implements Serializable {

    private String id;
    private String email;
    private String username;


    public UserResponse() {
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
