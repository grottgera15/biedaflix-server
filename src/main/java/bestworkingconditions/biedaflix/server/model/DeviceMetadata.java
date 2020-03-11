package bestworkingconditions.biedaflix.server.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Document(collection = "devices")
@Getter
@Setter
@NoArgsConstructor
public class DeviceMetadata {

    @Id
    private String id;

    private String userId;
    private String deviceDetails;
    private String location;
    private Date lastLoggedIn;

    public DeviceMetadata(String deviceDetails, String location, Date lastLoggedIn) {
        this.deviceDetails = deviceDetails;
        this.location = location;
        this.lastLoggedIn = lastLoggedIn;
    }
}
