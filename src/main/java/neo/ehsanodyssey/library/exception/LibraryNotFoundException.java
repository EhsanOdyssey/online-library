package neo.ehsanodyssey.library.exception;

import lombok.Getter;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-15 Feb/Thu
 **/
@Getter
public class LibraryNotFoundException extends ResourceNotFoundException {

    public LibraryNotFoundException() {
        this((String) null);
    }

    public LibraryNotFoundException(String message) {
        this(message, null);
    }

    public LibraryNotFoundException(Throwable cause) {
        this((String) null, cause);
    }

    public LibraryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
