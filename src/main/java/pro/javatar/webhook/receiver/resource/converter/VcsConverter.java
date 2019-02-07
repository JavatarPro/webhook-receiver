/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.converter;

import pro.javatar.webhook.receiver.resource.model.VcsPushRequestTO;
import pro.javatar.webhook.receiver.service.model.VcsPushRequestBO;

import java.util.Map;
import java.util.Set;

/**
 * @author Borys Zora
 * @version 2018-10-20
 */
public abstract class VcsConverter {

    public VcsPushRequestBO toVcsPushRequestBO(VcsPushRequestTO pushRequestTO) {
        Map body = pushRequestTO.getBody();
        return new VcsPushRequestBO()
                        .withAuthors(retrieveAuthors(body))
                        .withCommitter(retrieveCommitter(body))
                        .withCommittedBranch(retrieveCommittedBranch(body))
                        .withRepo(retrieveCommittedRepo(body))
                        .withRepoOwner(retrieveCommittedRepoOwner(body));
    }

    abstract String retrieveCommitter(Map body);

    abstract Set<String> retrieveAuthors(Map body);

    abstract String retrieveCommittedBranch(Map body);

    abstract String retrieveCommittedRepo(Map body);

    abstract String retrieveCommittedRepoOwner(Map body);

}
