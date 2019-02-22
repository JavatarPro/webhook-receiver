package pro.javatar.webhook.receiver.resource.model;

import java.time.Instant;

/**
 * @author Borys Zora
 * @version 2019-02-21
 */
public class ExceptionResponse {

    private String code;

    private String message;

    private String devMessage;

    private String thrownAt = Instant.now().toString();

    private String l18nCode;

    private int status;

    private String path;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ExceptionResponse withCode(String code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExceptionResponse withMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDevMessage() {
        return devMessage;
    }

    public void setDevMessage(String devMessage) {
        this.devMessage = devMessage;
    }

    public ExceptionResponse withDevMessage(String devMessage) {
        this.devMessage = devMessage;
        return this;
    }

    public String getThrownAt() {
        return thrownAt;
    }

    public void setThrownAt(String thrownAt) {
        this.thrownAt = thrownAt;
    }

    public ExceptionResponse withThrownAt(String thrownAt) {
        this.thrownAt = thrownAt;
        return this;
    }

    public String getL18nCode() {
        return l18nCode;
    }

    public void setL18nCode(String l18nCode) {
        this.l18nCode = l18nCode;
    }

    public ExceptionResponse withL18nCode(String l18nCode) {
        this.l18nCode = l18nCode;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ExceptionResponse withStatus(int status) {
        this.status = status;
        return this;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ExceptionResponse withPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", devMessage='" + devMessage + '\'' +
                ", thrownAt='" + thrownAt + '\'' +
                ", l18nCode='" + l18nCode + '\'' +
                ", status=" + status +
                ", path='" + path + '\'' +
                '}';
    }
}

