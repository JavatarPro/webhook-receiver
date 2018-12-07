/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.converter

/**
 * @author Borys Zora
 * @version 2018-10-21
 */
class BitbucketVcsConverter extends VcsConverter {

    @Override
    String retrieveCommitter(def body) {
        return body.actor.username
    }

    @Override
    Set<String> retrieveAuthors(def body) {
        return new HashSet<String>(body.push.changes.commits.author.user.username.get(0))
    }

    @Override
    String retrieveCommittedBranch(def body) {
        // TODO error handling
        if ("branch".equals(body.push.changes.new.type.get(0))) {
            return body.push.changes.new.name.get(0)
        }
        return ""
    }

    @Override
    String retrieveCommittedRepo(def body) {
        return body.repository.name
    }

    @Override
    String retrieveCommittedRepoOwner(def body) {
        return body.repository.owner.username
    }

}
