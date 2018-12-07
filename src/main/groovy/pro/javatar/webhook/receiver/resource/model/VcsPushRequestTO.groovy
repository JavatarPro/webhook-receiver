/*
 * Copyright (c) 2018 Javatar LLC
 * All rights reserved.
 */
package pro.javatar.webhook.receiver.resource.model

/**
 * @author Borys Zora
 * @version 2018-10-20
 */
class VcsPushRequestTO {

    Map<String, String> headers = new HashMap<>()

    Map<String, String> requestParams = new HashMap<>()

    def body = new HashMap()

    String rawBody = ""

    Map<String, String> getHeaders() {
        return headers
    }

    void setHeaders(Map<String, String> headers) {
        this.headers = headers
    }

    VcsPushRequestTO addHeader(String name, String value) {
        this.headers.put(name, value)
    }

    Map<String, String> getRequestParams() {
        return requestParams
    }

    void setRequestParams(Map<String, String> requestParams) {
        this.requestParams = requestParams
    }

    VcsPushRequestTO addRequestParams(String name, String value) {
        this.requestParams.put(name, value)
        return this
    }

    def getBody() {
        return body
    }

    void setBody(def body) {
        this.body = body
    }

    VcsPushRequestTO withBody(def body) {
        this.body = body
        return this
    }

    String getRawBody() {
        return rawBody
    }

    void setRawBody(String rawBody) {
        this.rawBody = rawBody
    }

    VcsPushRequestTO withRawBody(String rawBody) {
        this.rawBody = rawBody
        return this
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
