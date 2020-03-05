package bestworkingconditions.biedaflix.server.model;


import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Document(collection = "devices")
public class DeviceMetadata {

    @Id
    private String id;

    private String userId;
    private String deviceDetails;
    private String location;
    private Date lastLoggedIn;

    public DeviceMetadata() {
    }

    public DeviceMetadata(String deviceDetails, String location, Date lastLoggedIn) {
        this.deviceDetails = deviceDetails;
        this.location = location;
        this.lastLoggedIn = lastLoggedIn;
    }
}
