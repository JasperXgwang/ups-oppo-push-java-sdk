package com.ups.oppo.push.server.sdk.exception;

import java.io.IOException;

/**
 * Created by wangxinguo on 2016-8-21.
 */
public final class InvalidRequestException extends IOException {
    private static final long serialVersionUID = -7697907721869599315L;
    private final int status;
    private final String description;

    public InvalidRequestException(int status) {
        this(status, (String) null);
    }

    public InvalidRequestException(int status, String description) {
        super(getMessage(status, description));
        this.status = status;
        this.description = description;
    }

    private static String getMessage(int status, String description) {
        StringBuilder base = (new StringBuilder("HTTP Status Code: ")).append(status);
        if (description != null) {
            base.append("(").append(description).append(")");
        }

        return base.toString();
    }

    public int getHttpStatusCode() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }
}
