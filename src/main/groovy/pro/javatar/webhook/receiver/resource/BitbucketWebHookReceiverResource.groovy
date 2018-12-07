/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pro.javatar.webhook.receiver.service.model.JenkinsRemoteJobRequest
import pro.javatar.webhook.receiver.service.JenkinsWebHookService
import pro.javatar.webhook.receiver.service.VcsWebHookReceiverService

/**
 * @author Borys Zora
 * @version 2018-10-13
 */
@Slf4j("logger")
@RestController
@RequestMapping("bitbucket")
class BitbucketWebHookReceiverResource {

    VcsWebHookReceiverService webHookReceiverService

    JenkinsWebHookService jenkinsWebHookService

    ObjectMapper objectMapper = new ObjectMapper()

    @Autowired
    BitbucketWebHookReceiverResource(VcsWebHookReceiverService webHookReceiverService,
                                     JenkinsWebHookService jenkinsWebHookService) {
        this.webHookReceiverService = webHookReceiverService
        this.jenkinsWebHookService = jenkinsWebHookService
        objectMapper.findAndRegisterModules()
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity handleWebHook(@RequestBody String jsonBody,
                                 @RequestParam(required = false, value = "jobUrl") String jobUrl,
                                 @RequestHeader(value = "X-Request-UUID", required = false) String requestId,
                                 @RequestHeader(value = "X-Event-Key", required = false) String eventKey,
                                 @RequestHeader(value = "User-Agent", required = false) String userAgent,
                                 @RequestHeader(value = "X-Attempt-Number", required = false) int attemptNumber,
                                 @RequestHeader(value = "X-Hook-UUID", required = false) String hookId) {
        logger.info("body: ${jsonBody}")
        def body = objectMapper.readValue(jsonBody, HashMap.class)
        if (!webHookReceiverService.shouldTriggerJenkinsWebHook(body)) {
            logger.info("commit will be ignored, body was: ${body}")
            return ResponseEntity.ok(prepareResponse("commit will be ignored", ""))
        }
        JenkinsRemoteJobRequest request =
                webHookReceiverService.getJenkinsRemoteJobRequest(null)// TODO
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
