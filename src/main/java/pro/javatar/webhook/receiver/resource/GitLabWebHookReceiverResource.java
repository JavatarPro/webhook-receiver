/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.javatar.webhook.receiver.resource.converter.GitLabVcsConverter;
import pro.javatar.webhook.receiver.resource.converter.VcsConverter;
import pro.javatar.webhook.receiver.resource.model.VcsPushRequestTO;
import pro.javatar.webhook.receiver.service.JenkinsWebHookService;
import pro.javatar.webhook.receiver.service.VcsWebHookReceiverService;

import java.util.HashMap;
import java.util.Map;

/**
 * Author : Borys Zora
 * Date Created: 4/7/18 01:10
 */
@RestController
@RequestMapping("gitlab")
class GitLabWebHookReceiverResource {

    private static final Logger logger = LoggerFactory.getLogger(GitLabWebHookReceiverResource.class);

    static final String GITLAB_HEADER = "X-Gitlab-Token";

    VcsWebHookReceiverService webHookReceiverService;

    JenkinsWebHookService jenkinsWebHookService;

    VcsConverter vcsConverter = new GitLabVcsConverter();

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    GitLabWebHookReceiverResource(VcsWebHookReceiverService webHookReceiverService,
                                  JenkinsWebHookService jenkinsWebHookService) {
        this.webHookReceiverService = webHookReceiverService;
        this.jenkinsWebHookService = jenkinsWebHookService;
        objectMapper.findAndRegisterModules();
    }

    // TODO add all query params
    @PostMapping
    ResponseEntity handleWebHook(@RequestBody String jsonBody,
                                 @RequestHeader(value = "X-Gitlab-Token", required = false) String gitlabHeader) {
        logger.info("body: {}", jsonBody);
        var pushRequestTO = new VcsPushRequestTO()
                .withJobUrl(gitlabHeader)
                .withRawBody(jsonBody)
                .addHeader("X-Gitlab-Token", gitlabHeader);
        var requestBO = vcsConverter.toVcsPushRequestBO(pushRequestTO);
        if (!webHookReceiverService.shouldTriggerJenkinsWebHook(requestBO)) {
            logger.info("commit will be ignored, request was: ${requestBO.toString()}");
            return ResponseEntity.ok(prepareResponse("commit will be ignored", ""));
        }
        // TODO validate gitlabHeader should be build
        var request = webHookReceiverService.getJenkinsRemoteJobRequest(requestBO);
        String response = jenkinsWebHookService.triggerJenkinsJobRemotely(request);
        return ResponseEntity.ok(prepareResponse("jenkins web hook was triggered", response));
    }

    Map<String, String> prepareResponse(String message, String jenkinsResponse) {
        Map<String, String> response = new HashMap<>();
        response.put("webHookReceiver", message);
        response.put("jenkinsResponse", jenkinsResponse);
        return response;
    }

}
