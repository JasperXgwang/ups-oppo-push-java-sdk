package com.ups.oppo.push.server.sdk.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息推送结果
 *
 * @author wangxinguo
 * @date 2016-9-3
 */
public class PushResult {

    /**
     * save_message_content resp
     */
    private String message_id;
    private String messageId;
    private String registrationId;
    private String errorCode;
    private String errorMessage;
    private String task_id;
    private String status;
    /**
     * Code	     英文描述	                                        中文描述
     * 10000	Invalid Registration_id	registration_id         非法或无效
     * 10001	Registration_id Unsubscribe	registration_id     未订阅(包括推送开关关闭的设备)
     * 10002	Invalid Alias Name	alias name                  非法或无效
     * 10003	Alias Name Unsubscribe	alias name              未订阅(包括推送开关关闭的设备)
     * 10004	registrationId Repeat	                        注册Id重复
     * 10005	Alias Name Repeat	                            别名重复
     */
    private Map<String, List<String>> respTarget = new HashMap<String, List<String>>();

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, List<String>> getRespTarget() {
        return respTarget;
    }

    public void setRespTarget(Map<String, List<String>> respTarget) {
        this.respTarget = respTarget;
    }

    public void result2Data(String code, List<String> values) {
        List<String> resultList = this.getRespTarget().get(code);
        if (resultList == null) {
            resultList = new ArrayList<String>();
            this.getRespTarget().put(code, resultList);
        }
        resultList.addAll(values);
    }

    @Override
    public String toString() {
        return "PushResult{" +
                "message_id='" + message_id + '\'' +
                ", messageId='" + messageId + '\'' +
                ", registrationId='" + registrationId + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", task_id='" + task_id + '\'' +
                ", status='" + status + '\'' +
                ", respTarget=" + respTarget +
                '}';
    }
}
