package ch.saunah.saunahbackend.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ApiError {

    private Date timestamp;
    private HttpStatus status;
    private int statusCode;
    private String message;
    private List<String> errors;

    public ApiError(HttpStatus status, String message, List<String> errors) {
        super();
        timestamp = new Date(System.currentTimeMillis());
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

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

    public HttpStatus getStatus() {
        return status;
    }

    public int getStatusCode() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
