package bestworkingconditions.biedaflix.server.errorHandling;

import bestworkingconditions.biedaflix.server.errorHandling.model.ApiError;
import bestworkingconditions.biedaflix.server.errorHandling.model.ApiSubError;
import bestworkingconditions.biedaflix.server.errorHandling.model.ApiValidationError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static bestworkingconditions.biedaflix.server.errorHandling.model.ApiErrorUtils.buildResponseEntity;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,error,ex));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + "parameter is missing";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST,error,ex));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiSubError> apiValidationErrors = new ArrayList<>();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        for(FieldError error : fieldErrors){
            ApiValidationError apiValidationError = new ApiValidationError();
            apiValidationError.setField(error.getField());
            apiValidationError.setObject(error.getObjectName());
            apiValidationError.setRejectedValue(error.getRejectedValue());
            apiValidationError.setMessage(error.getDefaultMessage());

            apiValidationErrors.add(apiValidationError);
        }

        ApiError error = new ApiError();
        error.setStatus(HttpStatus.BAD_REQUEST);
        error.setCode(HttpStatus.BAD_REQUEST.value());
        error.setSubErrors(apiValidationErrors);
        error.setMessage(fieldErrors.size() + " of fields provided in request have failed validation!");
        error.setDebugMessage(ex.getMessage());

        return buildResponseEntity(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex){
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }
}
