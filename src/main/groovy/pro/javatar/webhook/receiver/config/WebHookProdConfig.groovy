/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * @author Borys Zora
 * @version 2018-11-27
 */
@Configuration
@ConfigurationProperties("web-hook-prod")
class WebHookProdConfig {

    private String jenkinsHost

    private String authorizationHeader

    String getJenkinsHost() {
        return jenkinsHost
    }

    void setJenkinsHost(String jenkinsHost) {
        this.jenkinsHost = jenkinsHost
    }

    String getAuthorizationHeader() {
        return authorizationHeader
    }

    void setAuthorizationHeader(String authorizationHeader) {
        this.authorizationHeader = authorizationHeader
    }

    @Override
    public String toString() {
        return "WebHookProdConfig{" +
                "jenkinsHost='" + jenkinsHost + '\'' +
                ", authorizationHeader='" + "********" + '\'' +
                '}';
    }

}
