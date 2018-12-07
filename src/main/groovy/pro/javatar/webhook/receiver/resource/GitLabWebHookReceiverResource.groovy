/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Qualifier
import pro.javatar.webhook.receiver.resource.converter.GitLabVcsConverter
import pro.javatar.webhook.receiver.resource.converter.VcsConverter
import pro.javatar.webhook.receiver.resource.model.VcsPushRequestTO
import pro.javatar.webhook.receiver.service.model.JenkinsRemoteJobRequest
import pro.javatar.webhook.receiver.service.JenkinsWebHookService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping

import org.springframework.web.bind.annotation.RestController
import pro.javatar.webhook.receiver.service.VcsWebHookReceiverService
import pro.javatar.webhook.receiver.service.model.VcsPushRequestBO

/**
 * Author : Borys Zora
 * Date Created: 4/7/18 01:10
 */
@Slf4j("logger")
@RestController
@RequestMapping("gitlab")
class GitLabWebHookReceiverResource {

    static final String GITLAB_HEADER = "X-Gitlab-Token"

    VcsWebHookReceiverService webHookReceiverService

    JenkinsWebHookService jenkinsWebHookService

    VcsConverter vcsConverter = new GitLabVcsConverter()

    ObjectMapper objectMapper = new ObjectMapper()

    @Autowired
    GitLabWebHookReceiverResource(VcsWebHookReceiverService webHookReceiverService,
                                  JenkinsWebHookService jenkinsWebHookService) {
        this.webHookReceiverService = webHookReceiverService
        this.jenkinsWebHookService = jenkinsWebHookService
        objectMapper.findAndRegisterModules()
    }

    // TODO add all query params
    @PostMapping
    ResponseEntity handleWebHook(@RequestBody String jsonBody,
                                 @RequestHeader(value = "X-Gitlab-Token", required = false) String gitlabHeader) {
        logger.info("body: ${jsonBody}")
        def body = objectMapper.readValue(jsonBody, HashMap.class)
        VcsPushRequestTO pushRequestTO = new VcsPushRequestTO()
                .withRawBody(jsonBody)
                .withBody(body)
                .addHeader("X-Gitlab-Token", gitlabHeader)
        VcsPushRequestBO requestBO = vcsConverter.toVcsPushRequestBO(pushRequestTO)
        if (!webHookReceiverService.shouldTriggerJenkinsWebHook(requestBO)) {
            logger.info("commit will be ignored, request was: ${requestBO.toString()}")
            return ResponseEntity.ok(prepareResponse("commit will be ignored", ""))
        }
        // TODO validate gitlabHeader should be build
        JenkinsRemoteJobRequest request =
                webHookReceiverService.getJenkinsRemoteJobRequest(requestBO)
        String response = jenkinsWebHookService.triggerJenkinsJobRemotely(request)
        return ResponseEntity.ok(prepareResponse("jenkins web hook was triggered", response))
    }

    Map<String, String> prepareResponse(String message, String jenkinsResponse) {
        Map<String, String> response = new HashMap<>()
        response.put("webHookReceiver", message)
        response.put("jenkinsResponse", jenkinsResponse)
        return response
    }

}
