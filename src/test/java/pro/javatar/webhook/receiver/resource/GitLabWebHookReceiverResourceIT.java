/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static pro.javatar.webhook.receiver.TestUtils.getFileAsString;

/**
 * Author : Borys Zora
 * Date Created: 4/7/18 09:49
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("integration-test")
public class GitLabWebHookReceiverResourceIT {

    private static final Logger logger = LoggerFactory.getLogger(GitLabWebHookReceiverResourceIT.class);

    @LocalServerPort
    private int port;

    @Autowired
    RestTemplate restTemplate;

    static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void setUp() throws Exception {
        objectMapper.findAndRegisterModules();
    }

    @Test
    public void handleWebHookWhenDevUser() throws IOException {
        String body = getFileAsString("gitlab/gitlab-dev-push-event.json");
        Map receiverResponse = sendPostRequest(body);
        assertThat(receiverResponse.get("webHookReceiver"), Is.is("jenkins web hook was triggered"));
    }

    @Test
    public void handleWebHookWhenJenkinsUser() throws IOException {
        String body = getFileAsString("gitlab/gitlab-jenkins-push-event.json");
        Map receiverResponse = sendPostRequest(body);
        assertThat(receiverResponse.get("webHookReceiver"), Is.is("commit will be ignored"));
    }

    @Test
    public void handleWebHookWhenDevCredentialsButAuthorJenkinsUser() throws IOException {
        String body = getFileAsString("gitlab/gitlab-dev-cred-but-jenkins-push-event.json");
        Map receiverResponse = sendPostRequest(body);
        assertThat(receiverResponse.get("webHookReceiver"), Is.is("commit will be ignored"));
    }


    Map sendPostRequest(String body) throws IOException {
        String gitlabHeaderValue = "/job/common/job/configuration-service/build?token=pipeline";
        logger.info("body: {}", body);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(GitLabWebHookReceiverResource.GITLAB_HEADER, gitlabHeaderValue);
        HttpEntity request = new HttpEntity(body, headers);
        String url = getHost() + "/gitlab";
        ResponseEntity<String> entity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        logger.info("statusCode: {}", entity.getStatusCode());
        assertThat(entity.getStatusCode(), Is.is(HttpStatus.OK));
        logger.info("handleWebHook body: {}", entity.getBody());
        return objectMapper.readValue(entity.getBody(), HashMap.class);
    }

    private String getHost() {
        return "http://localhost:" + port;
    }

}