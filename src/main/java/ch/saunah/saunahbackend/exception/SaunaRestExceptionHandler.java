package ch.saunah.saunahbackend.exception;

import ch.saunah.saunahbackend.dto.SaunahApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

/**
 * Handles exceptions during a Web Request
 */
@ControllerAdvice
public class SaunaRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers,
                                                                          HttpStatus status, WebRequest request) {
        SaunahApiResponse saunahApiResponse = new SaunahApiResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        return new ResponseEntity<Object>(saunahApiResponse, new HttpHeaders(), saunahApiResponse.getStatus());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        SaunahApiResponse saunahApiResponse = new SaunahApiResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        return new ResponseEntity<Object>(saunahApiResponse, new HttpHeaders(), saunahApiResponse.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        SaunahApiResponse saunahApiResponse = new SaunahApiResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
        return new ResponseEntity<Object>(saunahApiResponse, new HttpHeaders(), saunahApiResponse.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        SaunahApiResponse saunahApiResponse = new SaunahApiResponse(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage());
        return new ResponseEntity<Object>(saunahApiResponse, new HttpHeaders(), saunahApiResponse.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
                                                                     HttpStatus status, WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

        SaunahApiResponse saunahApiResponse = new SaunahApiResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getLocalizedMessage());
        return new ResponseEntity<Object>(saunahApiResponse, new HttpHeaders(), saunahApiResponse.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                     HttpStatus status, WebRequest request) {
        SaunahApiResponse saunahApiResponse = new SaunahApiResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        return new ResponseEntity<Object>(saunahApiResponse, new HttpHeaders(), saunahApiResponse.getStatus());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        SaunahApiResponse saunahApiResponse;
        if (ex instanceof IllegalArgumentException || ex instanceof BadCredentialsException
            || ex instanceof NullPointerException || ex instanceof ValidationException)
        {
            saunahApiResponse = new SaunahApiResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        else{
            saunahApiResponse = new SaunahApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        }
        return new ResponseEntity<Object>(saunahApiResponse, new HttpHeaders(), saunahApiResponse.getStatus());
    }

    @ExceptionHandler({ SaunahMailException.class})
    public ResponseEntity<Object> handleMailException(SaunahMailException ex, WebRequest request) {
        SaunahApiResponse saunahApiResponse = new SaunahApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<Object>(saunahApiResponse, new HttpHeaders(), saunahApiResponse.getStatus());
    }

    @ExceptionHandler({ SaunahLoginException.class})
    public ResponseEntity<Object> handleSaunahLoginException(SaunahLoginException ex, WebRequest request) {
        SaunahApiResponse saunahApiResponse = new SaunahApiResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<Object>(saunahApiResponse, new HttpHeaders(), saunahApiResponse.getStatus());
    }

}
