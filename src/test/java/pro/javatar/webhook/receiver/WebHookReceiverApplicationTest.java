/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import pro.javatar.webhook.receiver.config.WebHookConfig;
import pro.javatar.webhook.receiver.service.JenkinsWebHookService;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pro.javatar.webhook.receiver.TestUtils.getFileAsString;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebHookReceiverApplicationTest {

	private static final Logger logger = LoggerFactory.getLogger(WebHookReceiverApplicationTest.class);

	@LocalServerPort
	private int port;

	@Autowired
	WebHookConfig webHookConfig;

	@Autowired
	JenkinsWebHookService jenkinsWebHookService;

	RestTemplate restTemplate = mock(RestTemplate.class);

	@BeforeEach
	void setUp() {
		ResponseEntity<String> entity = mock(ResponseEntity.class);
		when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(String.class)))
				.thenReturn(entity);
		when(entity.getBody()).thenReturn("{}");
		jenkinsWebHookService.setRestTemplate(restTemplate);
	}

	@Test
	public void handleBitbucketWebHookRequestSuccessCase() throws IOException {
		given().
			queryParam("jobUrl", "https://example-jenkins-host/job/url/id").
			header("X-Request-UUID", "5cfc7dc9-f3d9-4213-9143-b47a1a2c7ea0").
			header("X-Event-Key", "repo:push").
			header("X-Event-Time", "Tue, 19 Feb 2019 22:31:30 GMT").
			header("X-Attempt-Number", "3").
			header("X-Hook-UUID", "7c17d44e-12e2-4da8-a24d-82b74d8e61d8").
			header("User-Agent", "Bitbucket-Webhooks/2.0").
			header("Content-Type", "application/json").
			body(getFileAsString("bitbucket/bitbucket-error-1.json")).
		when().
			post(getHost()+ "/bitbucket").
		then().
			statusCode(200);
	}

	@Test
	public void handleBitbucketWebHookRequestBadRequestUrlNotProvided() throws IOException {
		logger.info(webHookConfig.toString());

		given().
			// queryParam("jobUrl", ).
			header("X-Request-UUID", "5cfc7dc9-f3d9-4213-9143-b47a1a2c7ea0").
			header("X-Event-Key", "repo:push").
			header("X-Event-Time", "Tue, 19 Feb 2019 22:31:30 GMT").
			header("X-Attempt-Number", "3").
			header("X-Hook-UUID", "7c17d44e-12e2-4da8-a24d-82b74d8e61d8").
			header("User-Agent", "Bitbucket-Webhooks/2.0").
			header("Content-Type", "application/json").
			body(getFileAsString("bitbucket/bitbucket-error-1.json")).
		when().
			post(getHost()+ "/bitbucket").
		then().
			statusCode(400).
			assertThat();

	}

	private String getHost() {
		return "http://localhost:" + port;
	}

}
