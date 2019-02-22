package pro.javatar.webhook.receiver.resource.validator;

import pro.javatar.webhook.receiver.resource.model.VcsPushRequestTO;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Borys Zora
 * @version 2019-02-21
 */
public class PushRequestValidator {

    public boolean isValid(VcsPushRequestTO pushRequestTO) {
        return getValidationErrors(pushRequestTO).isEmpty();
    }

    // TODO other validations
    public List<String> getValidationErrors(VcsPushRequestTO push) {
        List<String> errors = new ArrayList<>();
        if (isBlank(push.getJobUrl())) {
            errors.add("jobUrl is mandatory request parameter. " +
                    "It should point to target CI/CD job that will handle request");
        }
        return errors;
    }
}
