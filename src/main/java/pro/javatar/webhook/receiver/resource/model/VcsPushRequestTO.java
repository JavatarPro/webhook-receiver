/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Borys Zora
 * @version 2018-10-20
 */
public class VcsPushRequestTO {

    String requestId = UUID.randomUUID().toString();

    Map<String, String> headers = new HashMap<>();

    Map<String, String> requestParams = new HashMap<>();

    @Deprecated
    Map<String, Object> body = new HashMap();

    String rawBody = "";

    String jobUrl = "";

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public VcsPushRequestTO withRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public VcsPushRequestTO addHeader(String name, String value) {
        this.headers.put(name, value);
        return this;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, String> requestParams) {
        this.requestParams = requestParams;
    }

    public VcsPushRequestTO addRequestParams(String name, String value) {
        this.requestParams.put(name, value);
        return this;
    }

    @Deprecated
    public Map<String, Object> getBody() {
        return body;
    }

    @Deprecated
    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    @Deprecated
    public VcsPushRequestTO withBody(Map<String, Object> body) {
        this.body = body;
        return this;
    }

    public String getRawBody() {
        return rawBody;
    }

    public void setRawBody(String rawBody) {
        this.rawBody = rawBody;
    }

    public VcsPushRequestTO withRawBody(String rawBody) {
        this.rawBody = rawBody;
        return this;
    }

    public String getJobUrl() {
        return jobUrl;
    }

    public void setJobUrl(String jobUrl) {
        this.jobUrl = jobUrl;
    }

    public VcsPushRequestTO withJobUrl(String jobUrl) {
        setJobUrl(jobUrl);
        return this;
    }

    @Override
    public String toString() {
        return "VcsPushRequestTO{" +
                "requestId='" + requestId + '\'' +
                ", headers=" + headers +
                ", requestParams=" + requestParams +
                ", rawBody='" + rawBody.length() + '\'' +
                ", jobUrl='" + jobUrl + '\'' +
                '}';
    }
}
