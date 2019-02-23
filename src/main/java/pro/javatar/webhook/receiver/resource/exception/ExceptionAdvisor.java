package pro.javatar.webhook.receiver.resource.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pro.javatar.webhook.receiver.resource.model.ExceptionResponse;

/**
 * @author Borys Zora
 * @version 2019-02-21
 */
@ControllerAdvice
public class ExceptionAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvisor.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleUnExpectedExceptions(Exception ex) {
        logger.error(ex.getMessage(), ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        var body = new ExceptionResponse()
                .withCode("500.WHR.unexpected.error")
                .withMessage("internal server error")
                .withDevMessage(ex.getMessage())
                .withStatus(status.value())
                .withPath("") //TODO
                .withL18nCode("500.WHR.unexpected.error");
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(WebHookRestException.class)
    public ResponseEntity<ExceptionResponse> handleRestExceptions(WebHookRestException ex) {
        var body =  new ExceptionResponse()
                .withCode(ex.getCode())
                .withMessage(ex.getMessage())
                .withDevMessage(ex.getDevMessage())
                .withStatus(ex.status.value())
                .withPath(ex.getPath())
                .withL18nCode(ex.getL18nCode());
        return new ResponseEntity<>(body, ex.getStatus());
    }

}