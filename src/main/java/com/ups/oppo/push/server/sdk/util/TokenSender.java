package com.ups.oppo.push.server.sdk.util;

import com.alibaba.fastjson.JSONObject;
import com.ups.oppo.push.server.sdk.common.Constants;
import com.ups.oppo.push.server.sdk.exception.InvalidAuthTokenException;
import com.ups.oppo.push.server.sdk.vo.AuthToken;
import com.ups.oppo.push.server.sdk.vo.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * User: jasperxgwang
 * Date: 2018-5-30 15:02
 */
public class TokenSender extends HttpClient {

    private static final Logger logger = LoggerFactory.getLogger(TokenSender.class);

    String appKey;
    String masterSecret;
    private String token;

    public String getToken() {
        return token;
    }

    public TokenSender(String appKey, String masterSecret) throws InvalidAuthTokenException {
        this.appKey = appKey;
        this.masterSecret = masterSecret;
        try {
            this.flushToken(appKey, masterSecret, 3);
        } catch (Exception e) {
            throw new InvalidAuthTokenException(e.getMessage());
        }
    }


    public ResultPack<AuthToken> flushToken(String appKey, String masterSecret) throws Exception {
        ResultPack<AuthToken> resultPack = flushToken(appKey, masterSecret, 0);
        resetToken(resultPack);
        return resultPack;
    }

    private void resetToken(ResultPack<AuthToken> resultPack) throws InvalidAuthTokenException {
        if (resultPack.isSucceed()) {
            token = resultPack.value().getAuth_token();
            logger.debug("reset token:{}", token);
        } else {
            logger.error("resetToken error", resultPack);
            throw new InvalidAuthTokenException("error auth token appKey:" + appKey + " masterSecret:" + masterSecret);
        }
    }

    /**
     * 获取最新token
     *
     * @param appKey
     * @param masterSecret
     * @return
     */
    public ResultPack<AuthToken> flushToken(String appKey, String masterSecret, int retries) throws IOException {
        final String _url = Constants.AUTH_URL;

        String timestamp = String.valueOf(System.currentTimeMillis());
        String sign = SHA256Utils.getSHA256StrJava(appKey + timestamp + masterSecret);

        final StringBuilder body = newBody("app_key", appKey);
        addParameter(body, "sign", sign);
        addParameter(body, "timestamp", timestamp);

        Sender<AuthToken> sender = new Sender<AuthToken>();
        ResultPack<AuthToken> resultPack = sender.execRetries(new ExecHttpApplication<AuthToken>() {
            @Override
            public ResultPack<AuthToken> request() throws IOException {
                HttpResult httpResult = post(_url, body.toString());
                if (httpResult == null) {
                    return null;
                }
                String code = httpResult.getCode();
                String msg = httpResult.getMessage();
                String data = httpResult.getData();
                if (Constants.SUCCESS_CODE.equals(code)) {
                    AuthToken respTarget = new AuthToken();
                    if (StringUtils.isNotBlank(data)) {
                        respTarget = JSONObject.parseObject(data, AuthToken.class);
                    }
                    return ResultPack.succeed(code, msg, respTarget);
                } else {
                    return ResultPack.failed(code, msg);
                }
            }

            @Override
            public boolean refreshToken() throws InvalidAuthTokenException {
                return false;
            }
        }, retries);

        resetToken(resultPack);
        return resultPack;
    }
}
