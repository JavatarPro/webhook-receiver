package pro.javatar.webhook.receiver.resource.converter;

import org.junit.jupiter.api.Test;
import pro.javatar.commons.reader.JsonReader;
import pro.javatar.webhook.receiver.resource.model.VcsPushRequestTO;
import pro.javatar.webhook.receiver.service.model.VcsPushRequestBO;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Borys Zora
 * @version 2019-02-22
 */
class VcsConverterTest {

    VcsConverter converter = new BitbucketVcsConverter();

    JsonReader jsonReader = new JsonReader();

    @Test
    void toVcsPushRequestBO() {
        VcsPushRequestTO to = getStub();
        VcsPushRequestBO bo = converter.toVcsPushRequestBO(to);
        assertThat(bo.getRemoteJobSubUrl(), is(to.getJobUrl()));
        assertThat(bo.getWebHookBody(), is(to.getRawBody()));
    }

    VcsPushRequestTO getStub() {
        String body = jsonReader.getStringFromFile("bitbucket/bitbucket-error-1.json");
        return new VcsPushRequestTO()
                .withJobUrl("/test-url/address")
                .withRawBody(body)
                .addHeader("Content-Type", "application/json")
                .addRequestParams("jobUrl", "/test-url/address")
                .withRequestId("1");
    }


}