/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.service.model;

import java.util.Set;

/**
 * @author Borys Zora
 * @version 2018-10-20
 */
public class VcsPushRequestBO {

    public String committer;

    public Set<String> authors;

    public String committedBranch;

    public String repo;

    public String repoOwner;

    public String webHookBody;

    public String remoteJobSubUrl;

    public String getCommitter() {
        return committer;
    }

    public void setCommitter(String committer) {
        this.committer = committer;
    }

    public VcsPushRequestBO withCommitter(String committer) {
        this.committer = committer;
        return this;
    }

    public Set<String> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<String> authors) {
        this.authors = authors;
    }

    public VcsPushRequestBO withAuthors(Set<String> authors) {
        this.authors = authors;
        return this;
    }

    public String getCommittedBranch() {
        return committedBranch;
    }

    public void setCommittedBranch(String committedBranch) {
        this.committedBranch = committedBranch;
    }

    public VcsPushRequestBO withCommittedBranch(String committedBranch) {
        this.committedBranch = committedBranch;
        return this;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public VcsPushRequestBO withRepo(String repo) {
        this.repo = repo;
        return this;
    }

    public String getRepoOwner() {
        return repoOwner;
    }

    public void setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
    }

    public VcsPushRequestBO withRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
        return this;
    }

    public String getRemoteJobSubUrl() {
        return remoteJobSubUrl;
    }

    public void setRemoteJobSubUrl(String remoteJobSubUrl) {
        this.remoteJobSubUrl = remoteJobSubUrl;
    }

    public VcsPushRequestBO withRemoteJobSubUrl(String remoteJobSubUrl) {
        this.remoteJobSubUrl = remoteJobSubUrl;
        return this;
    }

    public String getWebHookBody() {
        return webHookBody;
    }

    public void setWebHookBody(String webHookBody) {
        this.webHookBody = webHookBody;
    }

    public VcsPushRequestBO withWebHookBody(String webHookBody) {
        this.webHookBody = webHookBody;
        return this;
    }

    @Override
    public String toString() {
        return "VcsPushRequestBO{" +
                "committer='" + committer + '\'' +
                ", authors=" + authors +
                ", committedBranch='" + committedBranch + '\'' +
                ", repo='" + repo + '\'' +
                ", repoOwner='" + repoOwner + '\'' +
                ", remoteJobSubUrl='" + remoteJobSubUrl + '\'' +
                '}';
    }
}
