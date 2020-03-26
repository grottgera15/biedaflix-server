package bestworkingconditions.biedaflix.server.identity.user.model;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

//ten lombok generuje automatycznie - najechac sobie np na data

@Data
@NoArgsConstructor
public class Device {

    @Id
    @Generated //czy jest ok?
    private Long id;

    private String userId;

    private String deviceDetails;

    private String location;

    private Data lastLoggedIn;
}