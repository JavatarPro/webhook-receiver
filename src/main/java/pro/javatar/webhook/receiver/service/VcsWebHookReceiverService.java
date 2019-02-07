/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.javatar.webhook.receiver.config.WebHookConfig;
import pro.javatar.webhook.receiver.service.model.JenkinsRemoteJobRequest;
import pro.javatar.webhook.receiver.service.model.VcsPushRequestBO;

import java.util.Set;

/**
 * @author Borys Zora
 * @version 2018-10-14
 */
@Service
public class VcsWebHookReceiverService {

    private static final Logger logger = LoggerFactory.getLogger(VcsWebHookReceiverService.class);

    protected WebHookConfig webHookConfig;

    public VcsWebHookReceiverService(WebHookConfig webHookConfig) {
        this.webHookConfig = webHookConfig;
    }

    public boolean shouldTriggerJenkinsWebHook(VcsPushRequestBO pushRequest) {
        if (isIgnoredUser(pushRequest.getCommitter())) {
            logger.info("user: ${pushRequest.getCommitter()} is ignored, it was technical commit");
            return false;
        }
        if (isAuthorOnlyIgnoredUser(pushRequest.getAuthors())) {
            logger.info("authors: ${pushRequest.getAuthors()} are only ignored users, it was technical commit");
            return false;
        }
        return isBranchAllowed(pushRequest);
    }

    public JenkinsRemoteJobRequest getJenkinsRemoteJobRequest(VcsPushRequestBO pushRequest) {
        return new JenkinsRemoteJobRequest()
                .withRepo(pushRequest.getRepo())
                .withRepoOwner(pushRequest.getRepoOwner())
                .withBody(pushRequest.getWebHookBody())
                .withRemoteJobSubUrl(pushRequest.getRemoteJobSubUrl());
    }

    boolean isAuthorOnlyIgnoredUser(Set<String> authors) {
        for(String author: authors) {
            if (!webHookConfig.getIgnoredUser().equalsIgnoreCase(author)) {
                return false;
            }
        }
        return true;
    }

    boolean isIgnoredUser(String committer) {
        return webHookConfig.getIgnoredUser().equalsIgnoreCase(committer);
    }

    boolean isBranchAllowed(VcsPushRequestBO pushRequest) {
        // TODO depends which branches for which repo is allowed
//        return webHookConfig.allowedBranch.equalsIgnoreCase(pushRequest.getCommittedBranch());
        return false; // TODO
    }

}