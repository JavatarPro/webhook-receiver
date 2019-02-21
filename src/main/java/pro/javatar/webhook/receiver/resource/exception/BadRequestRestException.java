package pro.javatar.webhook.receiver.resource.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Borys Zora
 * @version 2019-02-21
 */
public class BadRequestRestException extends WebHookRestException {

    public BadRequestRestException() {}

    public BadRequestRestException(String message) {
        super(message);
        status = HttpStatus.BAD_REQUEST;
        devMessage = "client side mistake, no action needed";
    }

    @Override
    public String getCode() {
        return "400.WHR.BadRequest";
    }

}
