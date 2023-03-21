package peaksoft.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author kurstan
 * @created at 21.03.2023 11:41
 */
@Getter
@Setter
public class ExceptionResponse {
    private HttpStatus httpStatus;
    private String exceptionClassName;
    private String message;

    public ExceptionResponse(HttpStatus httpStatus, String exceptionClassName, String message) {
        this.httpStatus = httpStatus;
        this.exceptionClassName = exceptionClassName;
        this.message = message;
    }
}
