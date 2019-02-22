/*
 * Copyright (c) 2019 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.exception;

import org.springframework.http.HttpStatus;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author Borys Zora
 * @version 2019-02-21
 */
public abstract class WebHookRestException extends RuntimeException {

    protected HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    protected String devMessage;

    protected String l18nCode;

    protected String path;

    public WebHookRestException() {}

    public WebHookRestException(String message) {
        super(message);
    }

    public String getDevMessage() {
        return devMessage;
    }

    public void setDevMessage(String devMessage) {
        this.devMessage = devMessage;
    }

    public WebHookRestException withDevMessage(String devMessage) {
        this.devMessage = devMessage;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public WebHookRestException withStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public abstract String getCode();

    public void setL18nCode(String l18nCode) {
        this.l18nCode = l18nCode;
    }

    public WebHookRestException withL18nCode(String l18nCode) {
        this.l18nCode = l18nCode;
        return this;
    }

    public String getL18nCode() {
        if (isNotBlank(l18nCode)) {
            return l18nCode;
        }
        return getCode();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public WebHookRestException withPath(String path) {
        this.path = path;
        return this;
    }

}
