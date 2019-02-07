/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Borys Zora
 * @version 2018-10-20
 */
public class VcsPushRequestTO {

    Map<String, String> headers = new HashMap<>();

    Map<String, String> requestParams = new HashMap<>();

    Map body = new HashMap();

    String rawBody = "";

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

    public Map getBody() {
        return body;
    }

    public void setBody(Map body) {
        this.body = body;
    }

    public VcsPushRequestTO withBody(Map body) {
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

    @Override
    public String toString() {
        return "VcsPushRequestTO{" +
                "headers=" + headers +
                ", requestParams=" + requestParams +
                ", body=" + body +
                ", rawBody='" + rawBody + '\'' +
                '}';
    }
}
