package neo.ehsanodyssey.library.rest.handler.response;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-16 Feb/Fri
 **/
@Getter
abstract class AbstractResponseBody {

    private final boolean success;
    private final LocalDateTime timestamp;

    protected AbstractResponseBody(boolean success, LocalDateTime timestamp) {
        this.success = success;
        this.timestamp = timestamp;
    }
}
