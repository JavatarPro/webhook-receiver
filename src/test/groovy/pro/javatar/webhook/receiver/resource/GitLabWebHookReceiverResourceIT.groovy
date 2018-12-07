/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Test;
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

import static pro.javatar.webhook.receiver.TestUtils.getFileAsString
import static org.junit.Assert.*

/**
 * Author : Borys Zora
 * Date Created: 4/7/18 09:49
 */
@Slf4j("logger")
@RunWith(SpringRunner)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(GitLabWebHookReceiverResourceIT.SiblingsRouterApp.class)
@ActiveProfiles("integration-test")
class GitLabWebHookReceiverResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    RestTemplate restTemplate

    ObjectMapper objectMapper = new ObjectMapper()

    @Before
    void setUp() throws Exception {
        objectMapper.findAndRegisterModules()
    }

    @Test
    void handleWebHookWhenDevUser() {
        String body = getFileAsString("gitlab-dev-push-event.json")
        def receiverResponse = sendPostRequest(body)
        assertThat(receiverResponse.webHookReceiver, Is.is("jenkins web hook was triggered"))
    }

    @Test
    void handleWebHookWhenJenkinsUser() {
        String body = getFileAsString("gitlab-jenkins-push-event.json")
        def receiverResponse = sendPostRequest(body)
        assertThat(receiverResponse.webHookReceiver, Is.is("commit will be ignored"))
    }

    @Test
    void handleWebHookWhenDevCredentialsButAuthorJenkinsUser() {
        String body = getFileAsString("gitlab-dev-cred-but-jenkins-push-event.json")
        def receiverResponse = sendPostRequest(body)
        assertThat(receiverResponse.webHookReceiver, Is.is("commit will be ignored"))
    }


    def sendPostRequest(String body) {
        String gitlabHeaderValue = "/job/common/job/configuration-service/build?token=pipeline"
        logger.info("body: ${body}")
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(GitLabWebHookReceiverResource.GITLAB_HEADER, gitlabHeaderValue)
        HttpEntity request = new HttpEntity(body, headers)
        String url = "${getHost()}/gitlab"
        HttpEntity<String> entity = restTemplate.exchange(url, HttpMethod.POST, request, String.class)
        logger.info("statusCode: ${entity.statusCode}")
        assertThat(entity.getStatusCode(), Is.is(HttpStatus.OK))
        logger.info("handleWebHook body: ${entity.body}")
        return objectMapper.readValue(entity.getBody(), HashMap.class)
    }

    private String getHost() {
        return "http://localhost:" + port
    }

    @RestController
    static class SiblingsRouterApp {

        @GetMapping(path = "/job/common/job/configuration-service/build")
        ResponseEntity getInventory(@RequestParam String token,
                                    @RequestParam String repo,
                                    @RequestParam String repoOwner,
                                    @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
            assertThat(token, Is.is("pipeline"))
            assertThat(repo, Is.is("configuration-service"))
            assertThat(repoOwner, Is.is("services"))
            assertThat(auth, Is.is("Basic amVua2luc1dlYkhvb2s6amVua2luc1dlYkhvb2s="))
            return ResponseEntity.ok("repo: " + repo);
        }
    }

}