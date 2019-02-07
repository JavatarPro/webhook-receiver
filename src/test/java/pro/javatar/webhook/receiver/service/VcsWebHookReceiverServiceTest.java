/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import pro.javatar.webhook.receiver.config.WebHookConfig;
import pro.javatar.webhook.receiver.resource.converter.GitLabVcsConverter;
import pro.javatar.webhook.receiver.resource.converter.VcsConverter;
import pro.javatar.webhook.receiver.resource.model.VcsPushRequestTO;
import pro.javatar.webhook.receiver.service.model.JenkinsRemoteJobRequest;
import pro.javatar.webhook.receiver.service.model.VcsPushRequestBO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThat;
import static pro.javatar.webhook.receiver.TestUtils.getFileAsString;

/**
 * Author : Borys Zora
 * Date Created: 4/9/18 02:27
 */
class VcsWebHookReceiverServiceTest {

    VcsWebHookReceiverService service;

    VcsConverter vcsConverterService = new GitLabVcsConverter();

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    void setUp() throws Exception {
        WebHookConfig webHookConfig = new WebHookConfig();
        webHookConfig.setJenkinsHost("http://localhost:8080");
        webHookConfig.setIgnoredUser("jenkins");
        webHookConfig.setAllowedBranch("develop");
        service = new VcsWebHookReceiverService(webHookConfig);
        objectMapper.findAndRegisterModules();
    }

    @Test
    void shouldTriggerJenkinsWebHook() throws IOException {
        VcsPushRequestBO request = getVcsPushRequestBO("gitlab/gitlab-dev-push-event.json");
        assertThat(service.shouldTriggerJenkinsWebHook(request), Is.is(true));
    }

    @Test
    void shouldNotTriggerJenkinsWebHookWrongBranch() throws IOException {
        VcsPushRequestBO request = getVcsPushRequestBO("gitlab/gitlab-test-push-event.json");
        assertThat(service.shouldTriggerJenkinsWebHook(request), Is.is(false));
    }

    @Test
    void shouldNotTriggerJenkinsWebHookWrongUser() throws IOException {
        VcsPushRequestBO request = getVcsPushRequestBO("gitlab/gitlab-jenkins-push-event.json");
        assertThat(service.shouldTriggerJenkinsWebHook(request), Is.is(false));
    }

    @Test
    void shouldNotTriggerJenkinsWebHookWrongAuthor() throws IOException {
        VcsPushRequestBO request = getVcsPushRequestBO("gitlab/gitlab-dev-cred-but-jenkins-push-event.json");
        assertThat(service.shouldTriggerJenkinsWebHook(request), Is.is(false));
    }

    @Test
    void isAuthorIgnoredUserPositiveCase() throws IOException {
        VcsPushRequestBO request = getVcsPushRequestBO("gitlab/gitlab-dev-cred-but-jenkins-push-event.json");
        assertThat(service.isAuthorOnlyIgnoredUser(request.getAuthors()), Is.is(true));
    }

    @Test
    void isAuthorIgnoredUserNegativeCase() throws IOException {
        VcsPushRequestBO request = getVcsPushRequestBO("gitlab/gitlab-dev-push-event.json");
        assertThat(service.isAuthorOnlyIgnoredUser(request.getAuthors()), Is.is(false));
    }

    @Test
    void getJenkinsRemoteJobRequest() throws IOException {
        // /job/common/job/api-gateway-service/buildWithParameters?token=pipeline
        // /job/common/job/eureka-service/buildWithParameters?token=pipeline
        String remoteJobSubUrl = "/job/common/job/configuration-service/buildWithParameters?token=pipeline";
        VcsPushRequestBO pushRequestBO = getVcsPushRequestBO("gitlab/gitlab-dev-push-event.json")
                .withRemoteJobSubUrl(remoteJobSubUrl);
        JenkinsRemoteJobRequest request = service.getJenkinsRemoteJobRequest(pushRequestBO);
        JenkinsRemoteJobRequest expected = new JenkinsRemoteJobRequest();
        expected.setRepo("configuration-service");
        expected.setRepoOwner("services");
        expected.setRemoteJobSubUrl(remoteJobSubUrl);
        expected.setBody(pushRequestBO.getWebHookBody());
        assertThat(request, Is.is(expected));

        Map<JenkinsRemoteJobRequest, String> map = new HashMap<>();
        map.put(request, "value");
        assertThat(map.get(request), Is.is("value"));
        map.put(expected, "otherValue");
        assertThat(map.get(request),  Is.is("otherValue"));
        assertThat(map.get(expected),  Is.is("otherValue"));
    }

    VcsPushRequestBO getVcsPushRequestBO(String jsonFileInClasspath) throws IOException {
        String jsonBody = getFileAsString(jsonFileInClasspath);
        Map body = objectMapper.readValue(jsonBody, HashMap.class);
        VcsPushRequestTO pushRequestTO = new VcsPushRequestTO().withBody(body);
        return vcsConverterService.toVcsPushRequestBO(pushRequestTO)
                .withWebHookBody(jsonBody);
    }
}