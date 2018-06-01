package com.ups.oppo.push.server.sdk.util;

import com.ups.oppo.push.server.sdk.exception.ErrorCode;

import java.io.IOException;
import java.util.Random;

/**
 * User: jasperxgwang
 * Date: 2018-5-30 15:38
 */
public class Sender<T> {


    protected final Random random = new Random();


    public ResultPack<T> execRetries(ExecHttpApplication<T> exec, int retries) throws IOException {
        int attempt = 0;
        ResultPack<T> result;
        int backoff = 1000;
        boolean tryAgain;
        do {
            ++attempt;
            //执行请求
            result = exec.request();

            //是否非法token
            if (result != null) {
                if (isInvalidToken(result.getErrorCode())) {
                    int refreshTokenAttempt = 0;
                    boolean refreshTokenTryAgain = false;
                    int refreshTokenBackoff = 500;
                    do {
                        boolean success = exec.refreshToken();
                        if (success) {
                            ++refreshTokenAttempt;
                            //刷新成功，重新执行
                            result = exec.request();
                        }
                        refreshTokenBackoff = getBackoffTime(refreshTokenBackoff, refreshTokenTryAgain);
                        refreshTokenTryAgain = success && isInvalidToken(result.getErrorCode()) && refreshTokenAttempt <= 2;
                    } while (refreshTokenTryAgain);
                }
            }

            tryAgain = result == null && attempt <= retries;
            backoff = getBackoffTime(backoff, tryAgain);
        } while (tryAgain);
        if (result == null) {
            throw new IOException(String.format("Could not send message after [%s] attempts", attempt));
        } else {
            return result;
        }
    }

    private boolean isInvalidToken(ErrorCode errorCode) {
        return errorCode != null && ErrorCode.INVALID_AUTHTOKEN == errorCode;
    }


    private int getBackoffTime(int backoff, boolean tryAgain) {
        if (tryAgain) {
            int sleepTime = backoff / 2 + this.random.nextInt(backoff);
            this.sleep((long) sleepTime);
            if (2 * backoff < 60000) {
                backoff *= 2;
            }
        }
        return backoff;
    }

    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
