/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.converter;

import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Borys Zora
 * @version 2018-10-21
 */
public class BitbucketVcsConverter extends VcsConverter {

    private static final Logger logger = LoggerFactory.getLogger(GitLabVcsConverter.class);

    @Override
    public String retrieveCommitter(String body) {
        // Map.get("body.actor.username");
        return JsonPath.read(body, "$.actor.username");
    }

    @Override
    public Set<String> retrieveAuthors(String body) {
        // return new HashSet<String>(body.push.changes.commits.author.user.username.get(0));
        return new HashSet<>(JsonPath.read(body, "$.push.changes[*].commits[*].author.user.username"));
    }

    @Override
    public String retrieveCommittedBranch(String body) {
//        if ("branch".equals(body.push.changes.new.type.get(0))) {
//            return body.push.changes.new.name.get(0);
//        }
        String branch = JsonPath.read(body, "$.push.changes[0].new.type");
        if ("branch".equals(branch)) {
            return JsonPath.read(body, "$.push.changes[0].new.name");
        }
        return "";
    }

    @Override
    public String retrieveCommittedRepo(String body) {
        // return body.repository.name;
        return JsonPath.read(body, "$.repository.name");
    }

    @Override
    public String retrieveCommittedRepoOwner(String body) {
        // return body.repository.owner.username;
        return JsonPath.read(body, "$.repository.owner.username");
    }

}