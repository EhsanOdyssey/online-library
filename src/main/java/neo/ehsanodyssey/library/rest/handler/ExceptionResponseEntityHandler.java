package neo.ehsanodyssey.library.rest.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import neo.ehsanodyssey.library.exception.ServiceException;
import neo.ehsanodyssey.library.rest.handler.response.ExceptionBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-16 Feb/Fri
 **/
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionResponseEntityHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatusCode statusCode,
                                                             WebRequest request) {
        logException(ex, statusCode);
        return buildResponseEntity(new ExceptionBody(ex), statusCode);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        logException(ex, HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return buildResponseEntity(new ExceptionBody(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<Object> handleServiceException(ServiceException ex, WebRequest request) {
        logException(ex, ex.getStatus());
        return buildResponseEntity(new ExceptionBody(ex), ex.getStatus());
    }

    private static void logException(Exception ex, HttpStatusCode statusCode) {
        log.error("Exception occurred with status '{}': {}", statusCode, ex.getMessage(), ex);
    }

    private ResponseEntity<Object> buildResponseEntity(ExceptionBody error, HttpStatusCode statusCode) {
        return new ResponseEntity<>(error, statusCode);
    }
}
