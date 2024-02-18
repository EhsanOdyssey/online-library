package neo.ehsanodyssey.library.rest.handler.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-16 Feb/Fri
 **/
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExceptionBody extends AbstractResponseBody {

    private String message;
    private String debugMessage;

    public ExceptionBody() {
        super(false, LocalDateTime.now());
    }

    public ExceptionBody(Throwable ex) {
        this();
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ExceptionBody(String message, Throwable ex) {
        this();
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
