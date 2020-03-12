package bestworkingconditions.biedaflix.server.errorHandling;


import bestworkingconditions.biedaflix.server.errorHandling.model.ApiError;
import bestworkingconditions.biedaflix.server.errorHandling.model.ApiErrorUtils;
import bestworkingconditions.biedaflix.server.errorHandling.model.ApiValidationError;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class DatabaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    protected ResponseEntity<Object> handleDuplicateKeyException(DuplicateKeyException ex){

        String[] messageSplit = ex.getMessage().split(":");

        ApiValidationError apiValidationError = new ApiValidationError();

        List<String> parsedStrings = new ArrayList<>();
        for(String s : messageSplit){
            parsedStrings.add(s.replace(" ", "").replace("{","")
                                                                   .replace("}","")
                                                                   .replace("\"",""));
        }

        apiValidationError.setField(parsedStrings.get(parsedStrings.size()-2));
        apiValidationError.setRejectedValue(parsedStrings.get(parsedStrings.size()-1));
        apiValidationError.setMessage("Object Of given field value already exists in the database");
        ApiError error =new ApiError(HttpStatus.BAD_REQUEST,"Duplication of unique key",ex);

        error.setSubErrors(Collections.singletonList((apiValidationError)));

         return ApiErrorUtils.buildResponseEntity(error);
    }

}
