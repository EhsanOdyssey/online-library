package neo.ehsanodyssey.library.exception;

import lombok.Getter;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-15 Feb/Thu
 **/
@Getter
public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException() {
        this((String) null);
    }

    public UserNotFoundException(String message) {
        this(message, null);
    }

    public UserNotFoundException(Throwable cause) {
        this((String) null, cause);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
