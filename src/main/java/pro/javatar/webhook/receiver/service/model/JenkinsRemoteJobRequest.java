/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.service.model;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Borys Zora
 * @version 2018-10-13
 */
public class JenkinsRemoteJobRequest {

    private String remoteJobSubUrl;

    private String repo;

    private String repoOwner;

    private String body = "{}";

    public String getRemoteJobSubUrl() {
        if (isBlank(remoteJobSubUrl)) {
            return "";
        }
        if (remoteJobSubUrl.startsWith("/")) {
            return remoteJobSubUrl.substring(1);
        }
        return remoteJobSubUrl;
    }

    public void setRemoteJobSubUrl(String remoteJobSubUrl) {
        this.remoteJobSubUrl = remoteJobSubUrl;
    }

    public JenkinsRemoteJobRequest withRemoteJobSubUrl(String remoteJobSubUrl) {
        this.remoteJobSubUrl = remoteJobSubUrl;
        return this;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public JenkinsRemoteJobRequest withBody(String body) {
        this.body = body;
        return this;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public JenkinsRemoteJobRequest withRepo(String repo) {
        this.repo = repo;
        return this;
    }

    public String getRepoOwner() {
        return repoOwner;
    }

    public void setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
    }

    public JenkinsRemoteJobRequest withRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JenkinsRemoteJobRequest that = (JenkinsRemoteJobRequest) o;
        return Objects.equals(remoteJobSubUrl, that.remoteJobSubUrl) &&
                Objects.equals(repo, that.repo) &&
                Objects.equals(repoOwner, that.repoOwner) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remoteJobSubUrl, repo, repoOwner, body);
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
