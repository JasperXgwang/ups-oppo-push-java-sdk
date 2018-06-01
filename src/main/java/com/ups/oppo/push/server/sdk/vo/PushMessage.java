package com.ups.oppo.push.server.sdk.vo;

import com.ups.oppo.push.server.sdk.model.OppoMessage;

/**
 * User: jasperxgwang
 * Date: 2018-5-30 16:32
 */
public class PushMessage {

    private String target_type;
    private String target_value;
    private OppoMessage notification;

    public PushMessage() {
    }

    public PushMessage(String target_type, String target_value, OppoMessage notification) {
        this.target_type = target_type;
        this.target_value = target_value;
        this.notification = notification;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public String getTarget_value() {
        return target_value;
    }

    public void setTarget_value(String target_value) {
        this.target_value = target_value;
    }

    public OppoMessage getNotification() {
        return notification;
    }

    public void setNotification(OppoMessage notification) {
        this.notification = notification;
    }

    @Override
    public String toString() {
        return "PushMessage{" +
                "target_type='" + target_type + '\'' +
                ", target_value='" + target_value + '\'' +
                ", notification='" + notification + '\'' +
                '}';
    }
}
