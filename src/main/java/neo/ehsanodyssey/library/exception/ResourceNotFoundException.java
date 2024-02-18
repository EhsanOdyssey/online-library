package neo.ehsanodyssey.library.exception;

import org.springframework.http.HttpStatus;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-15 Feb/Thu
 **/
public class ResourceNotFoundException extends ServiceException {

    public ResourceNotFoundException() {
        this((String) null);
    }

    public ResourceNotFoundException(String message) {
        this(message, null);
    }

    public ResourceNotFoundException(Throwable cause) {
        this((String) null, cause);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(HttpStatus.NOT_FOUND, message, cause);
    }
}
