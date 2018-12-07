/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.service

import pro.javatar.webhook.receiver.config.WebHookConfig
import pro.javatar.webhook.receiver.service.model.JenkinsRemoteJobRequest
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

/**
 * Author : Borys Zora
 * Date Created: 4/8/18 12:17
 */
@Slf4j("logger")
@Service
class JenkinsWebHookService {

    RestTemplate restTemplate

    WebHookConfig webHookConfig

    HttpHeaders headers

    @Autowired
    JenkinsWebHookService(RestTemplate restTemplate, WebHookConfig webHookConfig) {
        this.restTemplate = restTemplate
        this.webHookConfig = webHookConfig
        headers = new HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, webHookConfig.getAuthorizationHeader())
    }

    String triggerJenkinsJobRemotely(JenkinsRemoteJobRequest jobRequest) {
        logger.info("triggerJenkinsJobRemotely: with jobRequest: ${jobRequest.toString()}")
        String jobUrl = createUrl(jobRequest)
        logger.info("triggerJenkinsJobRemotely jobUrl: ${jobUrl}")
        HttpEntity request = new HttpEntity(jobRequest.getBody(), headers)
        HttpEntity<String> response = restTemplate.exchange(jobUrl, HttpMethod.GET, request, String.class)
        logger.info("response for jobUrl: ${jobUrl} was ${response.toString()}")
        return response.getBody()
    }

    String getJenkinsJobRemoteUrl(String remoteJobSubUrl) {
        return "${webHookConfig.getJenkinsHost()}${remoteJobSubUrl}"
    }

    String createUrl(JenkinsRemoteJobRequest jobRequest) {
        String jobUrl = getJenkinsJobRemoteUrl(jobRequest.getRemoteJobSubUrl())
        return UriComponentsBuilder.fromHttpUrl(jobUrl).queryParam("repo", jobRequest.getRepo())
                .queryParam("repoOwner", jobRequest.getRepoOwner()).toUriString()
    }

}
