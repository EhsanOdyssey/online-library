package neo.ehsanodyssey.library.exception;

import lombok.Getter;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-15 Feb/Thu
 **/
@Getter
public class BookNotFoundException extends ResourceNotFoundException {

    public BookNotFoundException() {
        this((String) null);
    }

    public BookNotFoundException(String message) {
        this(message, null);
    }

    public BookNotFoundException(Throwable cause) {
        this((String) null, cause);
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
