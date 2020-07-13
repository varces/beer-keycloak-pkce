package ar.com.clazz.beersapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * @author Cesar Vargas
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserExtraNotFoundException extends RuntimeException {

    public UserExtraNotFoundException(String message) {
        super(message);
    }
}
