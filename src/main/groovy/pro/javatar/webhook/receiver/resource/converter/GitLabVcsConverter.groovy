/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.converter

import static org.apache.commons.lang3.StringUtils.isBlank

/**
 * @author Borys Zora
 * @version 2018-10-21
 */
class GitLabVcsConverter extends VcsConverter {

    @Override
    String retrieveCommitter(def body) {
        return body.user_username
    }

    @Override
    Set<String> retrieveAuthors(def body) {
        Set<String> authors = new HashSet<>()
        for(String author: body.commits.author.name) {
            authors.add(author.toLowerCase())
        }
        return authors
    }

    @Override
    String retrieveCommittedBranch(def body) {
        String ref = body.ref
        if (isBlank(ref)) {
            return ""
        }
        return ref.replace("refs/heads/", "")
    }

    @Override
    String retrieveCommittedRepo(def body) {
        return body.project.name
    }

    @Override
    String retrieveCommittedRepoOwner(def body) {
        return body.project.namespace
    }

}
