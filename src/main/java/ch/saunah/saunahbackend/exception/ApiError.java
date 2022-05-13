package ch.saunah.saunahbackend.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * This class represents the response class when an error is occurred when a request was sent.
 */
public class ApiError {

    private Date timestamp;
    private HttpStatus status;
    private String message;
    private List<String> errors;

    /**
     * Creates an instance and sets the fields.
     *
     * @param status
     * @param message
     * @param errors
     */
    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        timestamp = new Date(System.currentTimeMillis());
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    /**
     *
     * @param status
     * @param message
     * @param error
     */
    public ApiError(HttpStatus status, String message, String error) {
        super();
        timestamp = new Date(System.currentTimeMillis());
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

    public Date getTimestamp(){
        return timestamp;
    }

    public int getStatusCode() {
        return status.value();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
