package neo.ehsanodyssey.library.exception;

import org.springframework.http.HttpStatus;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-15 Feb/Thu
 **/
public class ResourceAlreadyExistException extends ServiceException {

    public ResourceAlreadyExistException() {
        this((String) null);
    }

    public ResourceAlreadyExistException(String message) {
        this(message, null);
    }

    public ResourceAlreadyExistException(Throwable cause) {
        this((String) null, cause);
    }

    public ResourceAlreadyExistException(String message, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, message, cause);
    }
}
