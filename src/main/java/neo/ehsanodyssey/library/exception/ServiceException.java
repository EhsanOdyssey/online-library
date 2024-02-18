package neo.ehsanodyssey.library.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-15 Feb/Thu
 **/
@Getter
public class ServiceException extends RuntimeException {
    private HttpStatus status;

    public ServiceException() {
        this((String) null);
    }

    public ServiceException(HttpStatus status) {
        this(status, (String) null);
    }

    public ServiceException(String message) {
        this(message, null);
    }

    public ServiceException(HttpStatus status, String message) {
        this(status, message, null);
    }

    public ServiceException(Throwable cause) {
        this((String) null, cause);
    }

    public ServiceException(HttpStatus status, Throwable cause) {
        this(status, null, cause);
    }

    public ServiceException(String message, Throwable cause) {
        this(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
    }

    public ServiceException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
