/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import pro.javatar.webhook.receiver.config.WebHookConfig
import pro.javatar.webhook.receiver.resource.converter.BitbucketVcsConverter
import pro.javatar.webhook.receiver.resource.converter.VcsConverter
import pro.javatar.webhook.receiver.resource.model.VcsPushRequestTO
import pro.javatar.webhook.receiver.service.model.JenkinsRemoteJobRequest
import pro.javatar.webhook.receiver.service.model.VcsPushRequestBO

import static org.junit.Assert.assertThat
import static pro.javatar.webhook.receiver.TestUtils.getFileAsString

/**
 * Author : Borys Zora
 * Date Created: 4/9/18 02:27
 */
class VcsWebHookReceiverServiceWithBitbucketConverterTest {

    VcsWebHookReceiverService service

    VcsConverter vcsConverterService = new BitbucketVcsConverter()

    ObjectMapper objectMapper = new ObjectMapper()

    @Before
    void setUp() throws Exception {
        WebHookConfig webHookConfig = new WebHookConfig()
        webHookConfig.setJenkinsHost("http://localhost:8080")
        webHookConfig.setIgnoredUser("jenkins")
        webHookConfig.setAllowedBranch("develop")
        service = new VcsWebHookReceiverService(webHookConfig)
        objectMapper.findAndRegisterModules()
    }

    @Test
    void shouldTriggerJenkinsWebHook() {
        VcsPushRequestBO request = getVcsPushRequestBO("bitbucket/bitbucket-dev-push-event.json")
        assertThat(service.shouldTriggerJenkinsWebHook(request), Is.is(true))
    }

    @Ignore // TODO provide json
    @Test
    void shouldNotTriggerJenkinsWebHookWrongBranch() {
        VcsPushRequestBO request = getVcsPushRequestBO("bitbucket/bitbucket-test-push-event.json")
        assertThat(service.shouldTriggerJenkinsWebHook(request), Is.is(false))
    }

    @Ignore // TODO provide json
    @Test
    void shouldNotTriggerJenkinsWebHookWrongUser() {
        VcsPushRequestBO request = getVcsPushRequestBO("bitbucket/bitbucket-jenkins-push-event.json")
        assertThat(service.shouldTriggerJenkinsWebHook(request), Is.is(false))
    }

    @Ignore // TODO provide json
    @Test
    void shouldNotTriggerJenkinsWebHookWrongAuthor() {
        VcsPushRequestBO request = getVcsPushRequestBO("bitbucket/bitbucket-dev-cred-but-jenkins-push-event.json")
        assertThat(service.shouldTriggerJenkinsWebHook(request), Is.is(false))
    }

    @Ignore // TODO provide json
    @Test
    void isAuthorIgnoredUserPositiveCase() {
        VcsPushRequestBO request = getVcsPushRequestBO("bitbucket/bitbucket-dev-cred-but-jenkins-push-event.json")
        assertThat(service.isAuthorOnlyIgnoredUser(request.getAuthors()), Is.is(true))
    }

    @Test
    void isAuthorIgnoredUserNegativeCase() {
        VcsPushRequestBO request = getVcsPushRequestBO("bitbucket/bitbucket-dev-push-event.json")
        assertThat(service.isAuthorOnlyIgnoredUser(request.getAuthors()), Is.is(false))
    }

    @Test
    void getJenkinsRemoteJobRequest() {
        // /job/common/job/api-gateway-service/buildWithParameters?token=pipeline
        // /job/common/job/eureka-service/buildWithParameters?token=pipeline
        String remoteJobSubUrl = "/job/common/job/configuration-service/buildWithParameters?token=pipeline"
        VcsPushRequestBO pushRequestBO = getVcsPushRequestBO("bitbucket/bitbucket-dev-push-event.json")
                .withRemoteJobSubUrl(remoteJobSubUrl)
        JenkinsRemoteJobRequest request = service.getJenkinsRemoteJobRequest(pushRequestBO)
        JenkinsRemoteJobRequest expected = new JenkinsRemoteJobRequest()
        expected.setRepo("work-service")
        expected.setRepoOwner("javatar-pro-work")
        expected.setRemoteJobSubUrl(remoteJobSubUrl)
        expected.setBody(pushRequestBO.getWebHookBody())
        assertThat(request, Is.is(expected))

        Map<JenkinsRemoteJobRequest, String> map = new HashMap<>()
        map.put(request, "value")
        assertThat(map.get(request), Is.is("value"))
        map.put(expected, "otherValue")
        assertThat(map.get(request),  Is.is("otherValue"))
        assertThat(map.get(expected),  Is.is("otherValue"))
    }

    VcsPushRequestBO getVcsPushRequestBO(String jsonFileInClasspath) {
        String jsonBody = getFileAsString(jsonFileInClasspath)
        def body = objectMapper.readValue(jsonBody, HashMap.class)
        VcsPushRequestTO pushRequestTO = new VcsPushRequestTO().withBody(body)
        return vcsConverterService.toVcsPushRequestBO(pushRequestTO)
                .withWebHookBody(jsonBody)
    }
}