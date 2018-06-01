package com.ups.oppo.push.server.sdk.vo;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by wangxinguo on 2016-8-21.
 */
public final class HttpResult implements Serializable {

    private static final long serialVersionUID = 8030699726843716781L;
    private final String code;
    private final String message;
    private final String data;


    private HttpResult(HttpResult.Builder builder) {
        this.code = builder.code;
        this.message = builder.message;
        this.data = builder.data;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }

    public static final class Builder {
        private String code;
        private String message;
        private String data;

        public Builder() {
        }

        public HttpResult fromJson(JSONObject json) {
            this.code = json.getString("code");
            this.message = json.getString("message");
            this.data = json.getString("data");
            return this.build();
        }

        public HttpResult build() {
            return new HttpResult(this);
        }
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
