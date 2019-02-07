/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.converter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Borys Zora
 * @version 2018-10-21
 */
public class GitLabVcsConverter extends VcsConverter {

    @Override
    String retrieveCommitter(Map body) {
//        return body.user_username;
        return null;
    }

    @Override
    Set<String> retrieveAuthors(Map body) {
        Set<String> authors = new HashSet<>();
//        for(String author: body.commits.author.name) {
//            authors.add(author.toLowerCase());
//        }
        return authors;
    }

    @Override
    String retrieveCommittedBranch(Map body) {
        String ref = null; //body.ref;
        if (isBlank(ref)) {
            return "";
        }
        return ref.replace("refs/heads/", "");
    }

    @Override
    String retrieveCommittedRepo(Map body) {
        // return body.project.name;
        return null;
    }

    @Override
    String retrieveCommittedRepoOwner(Map body) {
        // return body.project.namespace;
        return null;
    }

}
