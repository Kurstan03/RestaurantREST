package peaksoft.exception.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.websocket.Decoder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import peaksoft.exception.*;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author kurstan
 * @created at 21.03.2023 11:47
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException e){
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }
    @ExceptionHandler(ExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleExistsException(ExistsException e){
        return new ExceptionResponse(
                HttpStatus.CONFLICT,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }
    @ExceptionHandler(NoVacancyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleExistsException(NoVacancyException e){
        return new ExceptionResponse(
                HttpStatus.CONFLICT,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }
    @ExceptionHandler(NoValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleExistsException(NoValidException e){
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }
//    @ExceptionHandler(BadCredentialsException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ExceptionResponse badCredentialsException(BadCredentialsException e){
//        return new ExceptionResponse(
//                HttpStatus.BAD_REQUEST,
//                e.getClass().getSimpleName(),
//                e.getMessage()
//        );
//    }
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse accessDeniedException(AccessDeniedException e){
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse notValidException(MethodArgumentNotValidException e){
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                e.getClass().getSimpleName(),
                Objects.requireNonNull(e.getFieldError()).getDefaultMessage()
        );
    }
    @ExceptionHandler(JWTVerificationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse denied(JWTVerificationException e){
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                e.getClass().getSimpleName(),
                e.getMessage()
        );
    }
}
