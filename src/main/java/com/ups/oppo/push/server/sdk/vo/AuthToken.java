package com.ups.oppo.push.server.sdk.vo;

/**
 * User: jasperxgwang
 * Date: 2018-5-30 14:41
 */
public class AuthToken {

    /**
     * 有效期默认为24小时，过期后无法使用
     */
    private String auth_token;
    private long create_time;

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "auth_token='" + auth_token + '\'' +
                ", create_time=" + create_time +
                '}';
    }
}
