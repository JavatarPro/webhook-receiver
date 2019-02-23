/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author Borys Zora
 * @version 2018-10-13
 */
@Component
@ConfigurationProperties("web-hook")
public class WebHookConfig {

    private String ignoredUser;

    private String jenkinsHost;

    private String allowedBranch;

    private String authorizationHeader;

    private String jenkinsJobSecurityToken;

    public String getIgnoredUser() {
        return ignoredUser;
    }

    public void setIgnoredUser(String ignoredUser) {
        this.ignoredUser = ignoredUser;
    }

    public String getJenkinsHost() {
        if (isBlank(jenkinsHost)) {
            return "";
        }
        if (jenkinsHost.endsWith("/")) {
            return jenkinsHost;
        }
        return jenkinsHost + "/";
    }

    public void setJenkinsHost(String jenkinsHost) {
        this.jenkinsHost = jenkinsHost;
    }

    public String getAllowedBranch() {
        if (isBlank(allowedBranch)) return "";
        return allowedBranch;
    }

    public void setAllowedBranch(String allowedBranch) {
        this.allowedBranch = allowedBranch;
    }

    public String getAuthorizationHeader() {
        return authorizationHeader;
    }

    public void setAuthorizationHeader(String authorizationHeader) {
        this.authorizationHeader = authorizationHeader;
    }

    public String getJenkinsJobSecurityToken() {
        return jenkinsJobSecurityToken;
    }

    public void setJenkinsJobSecurityToken(String jenkinsJobSecurityToken) {
        this.jenkinsJobSecurityToken = jenkinsJobSecurityToken;
    }

    @Override
    public String toString() {
        return "WebHookConfig{" +
                "ignoredUser='" + ignoredUser + '\'' +
                ", jenkinsHost='" + jenkinsHost + '\'' +
                ", allowedBranch='" + allowedBranch + '\'' +
                ", jenkinsJobSecurityToken= '******'" +
                ", authorizationHeader= '******'" +
                "}";
    }
}
