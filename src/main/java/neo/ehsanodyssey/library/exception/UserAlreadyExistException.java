package neo.ehsanodyssey.library.exception;

import lombok.Getter;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-15 Feb/Thu
 **/
@Getter
public class UserAlreadyExistException extends ResourceAlreadyExistException {

    public UserAlreadyExistException() {
        this((String) null);
    }

    public UserAlreadyExistException(String message) {
        this(message, null);
    }

    public UserAlreadyExistException(Throwable cause) {
        this((String) null, cause);
    }

    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
