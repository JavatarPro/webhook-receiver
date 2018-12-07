/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.service.model

/**
 * @author Borys Zora
 * @version 2018-10-20
 */
class VcsPushRequestBO {

    String committer

    Set<String> authors

    String committedBranch

    String repo

    String repoOwner

    String webHookBody

    String remoteJobSubUrl

    String getCommitter() {
        return committer
    }

    void setCommitter(String committer) {
        this.committer = committer
    }

    VcsPushRequestBO withCommitter(String committer) {
        this.committer = committer
        return this
    }

    Set<String> getAuthors() {
        return authors
    }

    void setAuthors(Set<String> authors) {
        this.authors = authors
    }

    VcsPushRequestBO withAuthors(Set<String> authors) {
        this.authors = authors
        return this
    }

    String getCommittedBranch() {
        return committedBranch
    }

    void setCommittedBranch(String committedBranch) {
        this.committedBranch = committedBranch
    }

    VcsPushRequestBO withCommittedBranch(String committedBranch) {
        this.committedBranch = committedBranch
        return this
    }

    String getRepo() {
        return repo
    }

    void setRepo(String repo) {
        this.repo = repo
    }

    VcsPushRequestBO withRepo(String repo) {
        this.repo = repo
        return this
    }

    String getRepoOwner() {
        return repoOwner
    }

    void setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner
    }

    VcsPushRequestBO withRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner
        return this
    }

    String getRemoteJobSubUrl() {
        return remoteJobSubUrl
    }

    void setRemoteJobSubUrl(String remoteJobSubUrl) {
        this.remoteJobSubUrl = remoteJobSubUrl
    }

    VcsPushRequestBO withRemoteJobSubUrl(String remoteJobSubUrl) {
        this.remoteJobSubUrl = remoteJobSubUrl
        return this
    }

    String getWebHookBody() {
        return webHookBody
    }

    void setWebHookBody(String webHookBody) {
        this.webHookBody = webHookBody
    }

    VcsPushRequestBO withWebHookBody(String webHookBody) {
        this.webHookBody = webHookBody
        return this
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
