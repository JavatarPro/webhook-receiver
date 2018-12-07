/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

import static org.apache.commons.lang3.StringUtils.isBlank

/**
 * @author Borys Zora
 * @version 2018-10-13
 */
@Configuration
@ConfigurationProperties("web-hook")
class WebHookConfig {

    private String ignoredUser

    private String jenkinsHost

    private String allowedBranch

    private String authorizationHeader

    String getIgnoredUser() {
        return ignoredUser
    }

    void setIgnoredUser(String ignoredUser) {
        this.ignoredUser = ignoredUser
    }

    String getJenkinsHost() {
        return jenkinsHost
    }

    void setJenkinsHost(String jenkinsHost) {
        this.jenkinsHost = jenkinsHost
    }

    String getAllowedBranch() {
        if (isBlank(allowedBranch)) return ""
        return allowedBranch
    }

    void setAllowedBranch(String allowedBranch) {
        this.allowedBranch = allowedBranch
    }

    String getAuthorizationHeader() {
        return authorizationHeader
    }

    void setAuthorizationHeader(String authorizationHeader) {
        this.authorizationHeader = authorizationHeader
    }

    @Override
    String toString() {
        return "WebHookConfig{" +
                "ignoredUser='" + ignoredUser + '\'' +
                ", jenkinsHost='" + jenkinsHost + '\'' +
                ", allowedBranch='" + allowedBranch + '\'' +
                ", authorizationHeader= '******'" +
                '}'
    }

}
