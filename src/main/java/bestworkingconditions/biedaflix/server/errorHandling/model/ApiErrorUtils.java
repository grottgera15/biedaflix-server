package bestworkingconditions.biedaflix.server.errorHandling.model;

import org.springframework.http.ResponseEntity;

public class ApiErrorUtils {

    public static ResponseEntity<Object> buildResponseEntity(ApiError apiError){
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }

}
