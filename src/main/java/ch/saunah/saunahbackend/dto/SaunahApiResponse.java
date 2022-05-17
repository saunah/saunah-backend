package ch.saunah.saunahbackend.dto;

import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * This class represents the response class when an error is occurred when a request was sent.
 */
public class SaunahApiResponse {

    private Date timestamp;
    private HttpStatus status;
    private String message;
    private String path;

    /**
     * Creates an instance and sets the fields.
     *
     * @param status
     * @param message
     */
    public SaunahApiResponse(HttpStatus status, String message) {
        timestamp = new Date(System.currentTimeMillis());
        this.status = status;
        this.message = message;
    }

    public Date getTimestamp(){
        return timestamp;
    }

    public int getCode() {
        return status.value();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
