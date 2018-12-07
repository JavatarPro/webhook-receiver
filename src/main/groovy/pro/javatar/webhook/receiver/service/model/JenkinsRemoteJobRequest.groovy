/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.service.model

/**
 * @author Borys Zora
 * @version 2018-10-13
 */
class JenkinsRemoteJobRequest {

    private String remoteJobSubUrl

    private String repo

    private String repoOwner

    private String body = "{}"

    String getRemoteJobSubUrl() {
        return remoteJobSubUrl
    }

    void setRemoteJobSubUrl(String remoteJobSubUrl) {
        this.remoteJobSubUrl = remoteJobSubUrl
    }

    JenkinsRemoteJobRequest withRemoteJobSubUrl(String remoteJobSubUrl) {
        this.remoteJobSubUrl = remoteJobSubUrl
        return this
    }

    String getBody() {
        return body
    }

    void setBody(String body) {
        this.body = body
    }

    JenkinsRemoteJobRequest withBody(String body) {
        this.body = body
        return this
    }

    String getRepo() {
        return repo
    }

    void setRepo(String repo) {
        this.repo = repo
    }

    JenkinsRemoteJobRequest withRepo(String repo) {
        this.repo = repo
        return this
    }

    String getRepoOwner() {
        return repoOwner
    }

    void setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner
    }

    JenkinsRemoteJobRequest withRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner
        return this
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        JenkinsRemoteJobRequest that = (JenkinsRemoteJobRequest) o

        if (body != that.body) return false
        if (remoteJobSubUrl != that.remoteJobSubUrl) return false
        if (repo != that.repo) return false
        if (repoOwner != that.repoOwner) return false

        return true
    }

    int hashCode() {
        int result
        result = (remoteJobSubUrl != null ? remoteJobSubUrl.hashCode() : 0)
        result = 31 * result + (repo != null ? repo.hashCode() : 0)
        result = 31 * result + (repoOwner != null ? repoOwner.hashCode() : 0)
        result = 31 * result + (body != null ? body.hashCode() : 0)
        return result
    }

    @Override
    public String toString() {
        return "JenkinsRemoteJobRequest{" +
                "remoteJobSubUrl='" + remoteJobSubUrl + '\'' +
                ", repo='" + repo + '\'' +
                ", repoOwner='" + repoOwner + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
