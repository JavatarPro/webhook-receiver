/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.converter;

import java.util.Map;
import java.util.Set;

/**
 * @author Borys Zora
 * @version 2018-10-21
 */
public class BitbucketVcsConverter extends VcsConverter {

    @Override
    public String retrieveCommitter(Map<String, Object> body) {
        // Map.get("body.actor.username");
        return getByDotNotation("actor.username", body);
    }

    @Override
    public Set<String> retrieveAuthors(Map body) {
        // return new HashSet<String>(body.push.changes.commits.author.user.username.get(0));
        var result = getByDotNotationFistItem("push.changes.commits.author.user.username", body);
        if (result == null) {
            return null;
        }
        return (Set<String>) result;
    }

    @Override
    public String retrieveCommittedBranch(Map body) {
//        if ("branch".equals(body.push.changes.new.type.get(0))) {
//            return body.push.changes.new.name.get(0);
//        }
        if ("branch".equals(getByDotNotationFistItem("push.changes.new.type", body))) {
            return getByDotNotationFistItem("push.changes.new.name", body, String.class);
        }
        return "";
    }

    @Override
    public String retrieveCommittedRepo(Map body) {
        // return body.repository.name;
        return getByDotNotation("repository.name", body);
    }

    @Override
    public String retrieveCommittedRepoOwner(Map body) {
        // return body.repository.owner.username;
        return getByDotNotation("repository.owner.username", body);
    }

}
