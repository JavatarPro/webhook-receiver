/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pro.javatar.webhook.receiver.resource.model.VcsPushRequestTO;
import pro.javatar.webhook.receiver.service.model.VcsPushRequestBO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Borys Zora
 * @version 2018-10-20
 */
public abstract class VcsConverter {

    private static final Logger logger = LoggerFactory.getLogger(VcsConverter.class);

    public VcsPushRequestBO toVcsPushRequestBO(VcsPushRequestTO pushRequestTO) {
        String body = pushRequestTO.getRawBody();
        if (body == null) {
            return null;
        }
        return new VcsPushRequestBO()
                        .withAuthors(retrieveAuthors(body))
                        .withCommitter(retrieveCommitter(body))
                        .withCommittedBranch(retrieveCommittedBranch(body))
                        .withRepo(retrieveCommittedRepo(body))
                        .withRepoOwner(retrieveCommittedRepoOwner(body));
    }

    abstract String retrieveCommitter(String body);

    abstract Set<String> retrieveAuthors(String body);

    abstract String retrieveCommittedBranch(String body);

    abstract String retrieveCommittedRepo(String body);

    abstract String retrieveCommittedRepoOwner(String body);

}
