/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.converter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Borys Zora
 * @version 2018-10-21
 */
public class BitbucketVcsConverter extends VcsConverter {

    @Override
    public String retrieveCommitter(Map body) {
        return null;// Map.get("body.actor.username");
    }

    @Override
    public Set<String> retrieveAuthors(Map body) {
        // return new HashSet<String>(body.push.changes.commits.author.user.username.get(0));
        return null;
    }

    @Override
    public String retrieveCommittedBranch(Map body) {
        // TODO error handling
//        if ("branch".equals(body.push.changes.new.type.get(0))) {
//            return body.push.changes.new.name.get(0);
//        }
        return "";
    }

    @Override
    public String retrieveCommittedRepo(Map body) {
        // return body.repository.name;
        return null;
    }

    @Override
    public String retrieveCommittedRepoOwner(Map body) {
        // return body.repository.owner.username;
        return null;
    }

}
