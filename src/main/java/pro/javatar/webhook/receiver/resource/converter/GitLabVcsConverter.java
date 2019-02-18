/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.converter;

import com.jayway.jsonpath.JsonPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Borys Zora
 * @version 2018-10-21
 */
public class GitLabVcsConverter extends VcsConverter {

    private static final Logger logger = LoggerFactory.getLogger(GitLabVcsConverter.class);

    @Override
    String retrieveCommitter(String body) {
        return JsonPath.read(body, "$.user_username");
    }

    @Override
    Set<String> retrieveAuthors(String body) {
        Set<String> authors = new HashSet<>();
        List<String> authorList = JsonPath.read(body, "$.commits[*].author.name");
        for(String author: authorList) {
            authors.add(author.toLowerCase());
        }
        return authors;
    }

    @Override
    String retrieveCommittedBranch(String body) {
        String ref = JsonPath.read(body, "$.ref");
        if (isBlank(ref)) {
            return "";
        }
        return ref.replace("refs/heads/", "");
    }

    @Override
    String retrieveCommittedRepo(String body) {
        return JsonPath.read(body, "$.project.name");
    }

    @Override
    String retrieveCommittedRepoOwner(String body) {
        return JsonPath.read(body, "$.project.namespace");
    }

}
