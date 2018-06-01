package com.ups.oppo.push.server.sdk.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * User: jasperxgwang
 * Date: 2018-5-30 9:21
 */
public class OppoMessage {

    /**
     * App 开发者自定义消息 Id，OPPO 推送平
     * 台根据此 ID 做去重处理， 对于广播推
     * 送相同app_message_id只会保存一次，
     * 对于单推相同 app_message_id 只会推
     * 送一次。
     */
    private String app_message_id;

    /* =============通知栏样式 begin=============  */
    /**
     * 设置在通知栏展示的通知栏标题, 【字
     * 数限制 1~32， 中英文均以一个计算】
     */
    private String title = "";
    /**
     * 子标题 设置在通知栏展示的通知栏标
     * 题, 【字数限制 1~10， 中英文均以一个
     * 计算】
     */
    private String sub_title = "";
    /**
     * 设置在通知栏展示的通知的内容,【必
     * 填， 字数限制 200 以内， 中英文均以一
     * 个计算】
     */
    private String content = "";
    /* =============通知栏样式 end=============  */



    /* =============点击动作 begin=============  */
    /**
     * 点击动作类型
     * 0， 启动应用；
     * 1， 打开应用内页（activity 的 intent action）；
     * 2，打开网页；
     * 4， 打开应用内页（activity）；
     * 【非必填， 默认值为 0】
     */
    private int click_action_type = 0;
    /**
     * 应用内页地址【click_action_type 为1 或 4 时必填， 长度 500】
     */
    private String click_action_activity = "";
    /**
     * 网页地址【click_action_type 为 2 必填， 长度 500】
     */
    private String click_action_url = "";
    /**
     * 动作参数， 打开应用内页或网页时传递
     * 给应用或网页【JSON 格式， 非必填】，
     * 字符数不能超过 4K， 示例：
     * {"key1":"value1","key2":"value2"}
     */
    private JSONObject action_parameters;

    /* =============点击动作 end=============  */


    /* =============推送时间 begin=============  */
    /**
     * 展示类型 (0, “即时” ),(1, “定时” )
     */
    private int show_time_type = 0;

    /**
     * 定时展示开始时间（根据 time_zone 转
     * 换成当地时间）， 时间的毫秒数
     */
    private long show_start_time;
    /**
     * 定时展示结束时间（根据 time_zone 转
     * 换成当地时间）， 时间的毫秒数
     */
    private long show_end_time;
    /**
     * 是否进离线消息 【非必填，默认值为true】
     */
    private boolean off_line = Boolean.TRUE;
    /**
     * 离线消息的存活时间(time_to_live)
     * (单位： 秒), 【off_line 值为 true 时，
     * 必填， 最长 10 天】
     */
    private int off_line_ttl = 3600 * 24;
    /* =============推送时间 end=============  */


    /* =============高级设置 begin=============  */
    /**
     * 定时推送 (0, “即时” ),(1, “定
     * 时” ), 【只对全部用户推送生效】
     */
    private int push_time_type = 0;

    /**
     * 定时推送开始时间（根据 time_zone 转
     * 换成当地时间）, 【push_time_type 为
     * 1 必填】， 时间的毫秒数
     */
    private long push_start_time;

    /**
     * 时区， 默认值：（GMT+08:00） 北京， 香
     * 港， 新加坡
     */
    private String time_zone;

    /**
     * 是否定速推送,【非必填， 默认值为
     * false】
     */
    private boolean fix_speed = Boolean.FALSE;
    /**
     * 定速速率【fixSpeed 为 true 时， 必填】
     */
    private long fix_speed_rate;

    /**
     * 仅支持 registrationId 或 aliasName两种推送方式 应用接收消息到达回执的回调 URL， 字
     * 数限制 200 以内，中英文均以一个计算。OPPO Push 服务器 POST 一个 JSON 数据到 call_back_url；
     */
    private String call_back_url;
    /**
     * App 开发者自定义回执参数， 字数限制
     * 50 以内， 中英文均以一个计算。
     */
    private String call_back_parameter;
    /* =============高级设置 end=============  */

    public OppoMessage setApp_message_id(String app_message_id) {
        this.app_message_id = app_message_id;
        return this;
    }

    public OppoMessage setTitle(String title) {
        this.title = title;
        return this;
    }

    public OppoMessage setSub_title(String sub_title) {
        this.sub_title = sub_title;
        return this;
    }

    public OppoMessage setContent(String content) {
        this.content = content;
        return this;
    }

    public OppoMessage setClick_action_type(int click_action_type) {
        this.click_action_type = click_action_type;
        return this;
    }

    public OppoMessage setClick_action_activity(String click_action_activity) {
        this.click_action_activity = click_action_activity;
        return this;
    }

    public OppoMessage setClick_action_url(String click_action_url) {
        this.click_action_url = click_action_url;
        return this;
    }

    public OppoMessage setAction_parameters(JSONObject action_parameters) {
        this.action_parameters = action_parameters;
        return this;
    }

    public OppoMessage setShow_time_type(int show_time_type) {
        this.show_time_type = show_time_type;
        return this;
    }

    public OppoMessage setShow_start_time(long show_start_time) {
        this.show_start_time = show_start_time;
        return this;
    }

    public OppoMessage setShow_end_time(long show_end_time) {
        this.show_end_time = show_end_time;
        return this;
    }

    public OppoMessage setOff_line(boolean off_line) {
        this.off_line = off_line;
        return this;
    }

    public OppoMessage setOff_line_ttl(int off_line_ttl) {
        this.off_line_ttl = off_line_ttl;
        return this;
    }

    public OppoMessage setPush_time_type(int push_time_type) {
        this.push_time_type = push_time_type;
        return this;
    }

    public OppoMessage setPush_start_time(long push_start_time) {
        this.push_start_time = push_start_time;
        return this;
    }

    public OppoMessage setTime_zone(String time_zone) {
        this.time_zone = time_zone;
        return this;
    }

    public OppoMessage setFix_speed(boolean fix_speed) {
        this.fix_speed = fix_speed;
        return this;
    }

    public OppoMessage setFix_speed_rate(long fix_speed_rate) {
        this.fix_speed_rate = fix_speed_rate;
        return this;
    }

    public OppoMessage setCall_back_url(String call_back_url) {
        this.call_back_url = call_back_url;
        return this;
    }

    public OppoMessage setCall_back_parameter(String call_back_parameter) {
        this.call_back_parameter = call_back_parameter;
        return this;
    }

    public String getApp_message_id() {
        return app_message_id;
    }

    public String getTitle() {
        return title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public String getContent() {
        return content;
    }

    public int getClick_action_type() {
        return click_action_type;
    }

    public String getClick_action_activity() {
        return click_action_activity;
    }

    public String getClick_action_url() {
        return click_action_url;
    }

    public JSONObject getAction_parameters() {
        return action_parameters;
    }

    public int getShow_time_type() {
        return show_time_type;
    }

    public long getShow_start_time() {
        return show_start_time;
    }

    public long getShow_end_time() {
        return show_end_time;
    }

    public boolean isOff_line() {
        return off_line;
    }

    public int getOff_line_ttl() {
        return off_line_ttl;
    }

    public int getPush_time_type() {
        return push_time_type;
    }

    public long getPush_start_time() {
        return push_start_time;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public boolean isFix_speed() {
        return fix_speed;
    }

    public long getFix_speed_rate() {
        return fix_speed_rate;
    }

    public String getCall_back_url() {
        return call_back_url;
    }

    public String getCall_back_parameter() {
        return call_back_parameter;
    }

    @Override
    public String toString() {
        return "OppoMessage{" +
                "app_message_id='" + app_message_id + '\'' +
                ", title='" + title + '\'' +
                ", sub_title='" + sub_title + '\'' +
                ", content='" + content + '\'' +
                ", click_action_type=" + click_action_type +
                ", click_action_activity='" + click_action_activity + '\'' +
                ", click_action_url='" + click_action_url + '\'' +
                ", action_parameters=" + action_parameters +
                ", show_time_type=" + show_time_type +
                ", show_start_time=" + show_start_time +
                ", show_end_time=" + show_end_time +
                ", off_line=" + off_line +
                ", off_line_ttl=" + off_line_ttl +
                ", push_time_type=" + push_time_type +
                ", push_start_time=" + push_start_time +
                ", time_zone='" + time_zone + '\'' +
                ", fix_speed=" + fix_speed +
                ", fix_speed_rate=" + fix_speed_rate +
                ", call_back_url='" + call_back_url + '\'' +
                ", call_back_parameter='" + call_back_parameter + '\'' +
                '}';
    }

    public static void main(String[] args) {
        OppoMessage msg = new OppoMessage()
                .setClick_action_url("c_a_u")
                .setApp_message_id("msgId")
                .setTitle("push title")
                .setContent("push content")
                .setAction_parameters(JSON.parseObject("{\"click_action_activity\":\"\",\"click_action_type\":0,\"click_action_url\":\"c_a_u\",\"content\":\"\",\"fix_speed\":false,\"fix_speed_rate\":0,\"off_line\":true,\"off_line_ttl\":86400,\"push_start_time\":0,\"push_time_type\":0,\"show_end_time\":0,\"show_start_time\":0,\"show_time_type\":0,\"sub_title\":\"\",\"title\":\"\"}\n"));
        String json = JSON.toJSONString(msg);
        System.out.println(json);

        OppoMessage msg2 = JSON.parseObject(json, OppoMessage.class);
        System.out.println(msg2);
    }
}
