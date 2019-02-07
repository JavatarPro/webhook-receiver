/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThat;
import static pro.javatar.webhook.receiver.TestUtils.getFileAsString;

/**
 * Author : Borys Zora
 * Date Created: 4/7/18 09:49
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(GitLabWebHookReceiverResourceIT.SiblingsRouterApp.class)
@ActiveProfiles("integration-test")
class GitLabWebHookReceiverResourceIT {

    private static final Logger logger = LoggerFactory.getLogger(GitLabWebHookReceiverResourceIT.class);

    @LocalServerPort
    private int port;

    @Autowired
    RestTemplate restTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    void setUp() throws Exception {
        objectMapper.findAndRegisterModules();
    }

    @Test
    void handleWebHookWhenDevUser() throws IOException {
        String body = getFileAsString("gitlab-dev-push-event.json");
        Map receiverResponse = sendPostRequest(body);
        assertThat(receiverResponse.get("webHookReceiver"), Is.is("jenkins web hook was triggered"));
    }

    @Test
    void handleWebHookWhenJenkinsUser() throws IOException {
        String body = getFileAsString("gitlab-jenkins-push-event.json");
        Map receiverResponse = sendPostRequest(body);
        assertThat(receiverResponse.get("webHookReceiver"), Is.is("commit will be ignored"));
    }

    @Test
    void handleWebHookWhenDevCredentialsButAuthorJenkinsUser() throws IOException {
        String body = getFileAsString("gitlab-dev-cred-but-jenkins-push-event.json");
        Map receiverResponse = sendPostRequest(body);
        assertThat(receiverResponse.get("webHookReceiver"), Is.is("commit will be ignored"));
    }


    Map sendPostRequest(String body) throws IOException {
        String gitlabHeaderValue = "/job/common/job/configuration-service/build?token=pipeline";
        logger.info("body: ${body}");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(GitLabWebHookReceiverResource.GITLAB_HEADER, gitlabHeaderValue);
        HttpEntity request = new HttpEntity(body, headers);
        String url = "${getHost()}/gitlab";
        HttpEntity<String> entity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        logger.info("statusCode: ${entity.statusCode}");
        assertThat(((ResponseEntity<String>) entity).getStatusCode(), Is.is(HttpStatus.OK));
        logger.info("handleWebHook body: ${entity.body}");
        return objectMapper.readValue(entity.getBody(), HashMap.class);
    }

    private String getHost() {
        return "http://localhost:" + port;
    }

    @RestController
    static class SiblingsRouterApp {

        @GetMapping(path = "/job/common/job/configuration-service/build")
        ResponseEntity getInventory(@RequestParam String token,
                                    @RequestParam String repo,
                                    @RequestParam String repoOwner,
                                    @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String auth) {
            assertThat(token, Is.is("pipeline"));
            assertThat(repo, Is.is("configuration-service"));
            assertThat(repoOwner, Is.is("services"));
            assertThat(auth, Is.is("Basic amVua2luc1dlYkhvb2s6amVua2luc1dlYkhvb2s="));
            return ResponseEntity.ok("repo: " + repo);
        }
    }

}