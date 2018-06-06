package com.ups.oppo.push.server.sdk.common;

import com.ups.oppo.push.server.sdk.exception.ErrorCode;

/**
 * Created by wangxinguo on 2016-8-21.
 */
public class Constants {

    public static final String CHAR_SET = "UTF-8";
    public static final String SDK_VERSION = "1.0.0.20180605";
    public static final String SUCCESS_CODE = String.valueOf(ErrorCode.SUCCESS.getValue());

    private static final String HOST = "https://api.push.oppomobile.com";

    /**
     * 获取ACCESS TOKEN
     */
    public static final String AUTH_URL = HOST + "/server/v1/auth";

    /**
     * 保存通知栏消息内容体
     */
    public static final String SAVE_MESSAGE_CONTENT_URL = HOST + "/server/v1/message/notification/save_message_content";


    /**
     * 广播推送-通知栏消息
     */
    public static final String NOTIFICATION_BROADCAST_URL = HOST + "/server/v1/message/notification/broadcast";

    /**
     * 单推-通知栏消息推送（message/notification/unicast）
     */
    public static final String NOTIFICATION_UNICAST_URL = HOST + "/server/v1/message/notification/unicast";


    /**
     * 批量单推-通知栏消息推送(message/notification/unicast_batch）
     */
    public static final String NOTIFICATION_UNICAST_BATCH_URL = HOST + "/server/v1/message/notification/unicast_batch";


    /**
     * 广播类推送统计（message/statistics）
     */
    public static final String STATISTICS_URL = HOST + "/server/v1/message/statistics";


    /**
     * 通知栏动作类型   1:自定义行为
     * 2:打开URL
     * 3:打开APP
     */
    public enum ActionType {

        NOTIFY_ACTIVITY(1),
        NOTIFY_WEB(2),
        NOTIFY_APP(3);

        ActionType(int val) {
            this.val = val;
        }

        private int val;

        public int getVal() {
            return val;
        }
    }

    /**
     * 消息类型
     */
    public enum MsgType {

        DIRECT(1, "透传异步消息"),
        STATUSBAR(3, "系统通知栏异步消息");

        MsgType(int val, String desc) {
            this.val = val;
            this.desc = desc;
        }

        private int val;

        private String desc;

        public int getVal() {
            return val;
        }

        public String getDesc() {
            return desc;
        }


    }
}
