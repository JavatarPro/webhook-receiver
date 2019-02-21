/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.javatar.webhook.receiver.resource.converter.BitbucketVcsConverter;
import pro.javatar.webhook.receiver.resource.converter.VcsConverter;
import pro.javatar.webhook.receiver.resource.exception.BadRequestRestException;
import pro.javatar.webhook.receiver.resource.exception.WebHookRestException;
import pro.javatar.webhook.receiver.resource.model.VcsPushRequestTO;
import pro.javatar.webhook.receiver.resource.validator.PushRequestValidator;
import pro.javatar.webhook.receiver.service.model.JenkinsRemoteJobRequest;
import pro.javatar.webhook.receiver.service.JenkinsWebHookService;
import pro.javatar.webhook.receiver.service.VcsWebHookReceiverService;
import pro.javatar.webhook.receiver.service.model.VcsPushRequestBO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Borys Zora
 * @version 2018-10-13
 */
@RestController
@RequestMapping("bitbucket")
class BitbucketWebHookReceiverResource {

    private static final Logger logger = LoggerFactory.getLogger(BitbucketWebHookReceiverResource.class);

    private VcsWebHookReceiverService webHookReceiverService;

    private JenkinsWebHookService jenkinsWebHookService;

    private VcsConverter vcsConverter = new BitbucketVcsConverter();

    private PushRequestValidator validator = new PushRequestValidator();

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    BitbucketWebHookReceiverResource(VcsWebHookReceiverService webHookReceiverService,
                                     JenkinsWebHookService jenkinsWebHookService) {
        this.webHookReceiverService = webHookReceiverService;
        this.jenkinsWebHookService = jenkinsWebHookService;
        objectMapper.findAndRegisterModules();
    }

    @ApiOperation(value = "handleBitbucketWebHookRequest")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Web hook handled successfully, already delivered to job execution"),
            @ApiResponse(code = 202, message = "Web hook request accepted successfully, will be delivered later"),
            @ApiResponse(code = 400, message = "Bad request, something should be changed in request")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity handleWebHook(@RequestBody String jsonBody,
                                 @RequestParam(required = false, value = "jobUrl") String jobUrl,
                                 @RequestHeader(value = "X-Request-UUID", required = false) String requestId,
                                 @RequestHeader(value = "X-Event-Key", required = false) String eventKey,
                                 @RequestHeader(value = "User-Agent", required = false) String userAgent,
                                 @RequestHeader(value = "X-Attempt-Number", required = false) int attemptNumber,
                                 @RequestHeader(value = "X-Hook-UUID", required = false) String hookId) {
        var requestTO = new VcsPushRequestTO()
                .withRequestId(requestId)
                .withJobUrl(jobUrl)
                .withRawBody(jsonBody)
                .addRequestParams("jobUrl", jobUrl)
                .addHeader("X-Request-UUID", requestId)
                .addHeader("X-Event-Key", eventKey)
                .addHeader("User-Agent", userAgent)
                .addHeader("X-Attempt-Number", String.valueOf(attemptNumber))
                .addHeader("X-Hook-UUID", hookId);
        logger.info("try to handle bitbucket web hook with: \njobUrl: {} \nbody: {}", jobUrl, jsonBody);
        logger.debug("requestTO: {}", requestTO);

        if (!validator.isValid(requestTO)) {
            String errorMessage = String.join(", ", validator.getValidationErrors(requestTO));
            throw new BadRequestRestException(errorMessage)
                    .withPath("/bitbucket?jobUrl=" + jobUrl);
        }

        var requestBO = vcsConverter.toVcsPushRequestBO(requestTO);

        if (!webHookReceiverService.shouldTriggerJenkinsWebHook(requestBO)) {
            logger.info("commit will be ignored, body was: {}", requestBO);
            return ResponseEntity.ok(prepareResponse("commit will be ignored", ""));
        }
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
