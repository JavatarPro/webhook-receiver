/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.converter

import pro.javatar.webhook.receiver.resource.model.VcsPushRequestTO
import pro.javatar.webhook.receiver.service.model.VcsPushRequestBO

/**
 * @author Borys Zora
 * @version 2018-10-20
 */
abstract class VcsConverter {

    VcsPushRequestBO toVcsPushRequestBO(VcsPushRequestTO pushRequestTO) {
        def body = pushRequestTO.getBody()
        return new VcsPushRequestBO()
                        .withAuthors(retrieveAuthors(body))
                        .withCommitter(retrieveCommitter(body))
                        .withCommittedBranch(retrieveCommittedBranch(body))
                        .withRepo(retrieveCommittedRepo(body))
                        .withRepoOwner(retrieveCommittedRepoOwner(body))
    }

    abstract String retrieveCommitter(def body)

    abstract Set<String> retrieveAuthors(def body)

    abstract String retrieveCommittedBranch(def body)

    abstract String retrieveCommittedRepo(def body)

    abstract String retrieveCommittedRepoOwner(def body)

}
