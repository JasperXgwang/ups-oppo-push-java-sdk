package com.ups.oppo.push.server.sdk.exception;

import java.io.IOException;

/**
 * Created by wangxinguo on 2016-8-21.
 */
public final class InvalidAuthTokenException extends IOException {

    /**
     * Constructs an {@code IOException} with {@code null}
     * as its error detail message.
     */
    public InvalidAuthTokenException(String msg) {
        super(msg);
    }
}
