package com.ups.oppo.push.server.sdk.server;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ups.oppo.push.server.sdk.common.Constants;
import com.ups.oppo.push.server.sdk.common.TargetType;
import com.ups.oppo.push.server.sdk.exception.InvalidAuthTokenException;
import com.ups.oppo.push.server.sdk.model.OppoMessage;
import com.ups.oppo.push.server.sdk.util.CollectionUtils;
import com.ups.oppo.push.server.sdk.util.ExecHttpApplication;
import com.ups.oppo.push.server.sdk.util.HttpClient;
import com.ups.oppo.push.server.sdk.util.NumberUtils;
import com.ups.oppo.push.server.sdk.util.ReflectUtils;
import com.ups.oppo.push.server.sdk.util.ResultPack;
import com.ups.oppo.push.server.sdk.util.Sender;
import com.ups.oppo.push.server.sdk.util.StringUtils;
import com.ups.oppo.push.server.sdk.util.TokenSender;
import com.ups.oppo.push.server.sdk.util.UpsUtils;
import com.ups.oppo.push.server.sdk.vo.HttpResult;
import com.ups.oppo.push.server.sdk.vo.PushMessage;
import com.ups.oppo.push.server.sdk.vo.PushResult;
import com.ups.oppo.push.server.sdk.vo.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 */
public class OppoSender extends HttpClient {


    private static final Logger logger = LoggerFactory.getLogger(OppoSender.class);

    private static Map<String, TokenSender> tokens = new ConcurrentHashMap<String, TokenSender>();

    private String appKey;
    private String masterSecret;

    public OppoSender(String appKey, String masterSecret) throws InvalidAuthTokenException {
        this.appKey = appKey;
        this.masterSecret = masterSecret;
        String key = UpsUtils.getTokenKey(appKey);
        TokenSender tokenSender = tokens.get(key);
        if (tokenSender == null) {
            tokenSender = new TokenSender(appKey, masterSecret);
            tokens.put(key, tokenSender);
        }
        super.setAuthToken(tokenSender.getToken());
    }

    /**
     * 保存通知栏消息内容体
     *
     * @param message
     * @return
     * @throws IOException
     */
    public ResultPack<String> saveMessageContent(OppoMessage message) throws IOException {
        return this.saveMessageContent(message, 0);
    }

    /**
     * 保存通知栏消息内容体
     *
     * @param message
     * @param retries
     * @return
     * @throws IOException
     */
    public ResultPack<String> saveMessageContent(OppoMessage message, int retries) throws IOException {
        if (message == null) {
            return ResultPack.failed("message is null");
        }

        final String _url = Constants.SAVE_MESSAGE_CONTENT_URL;

        Map<String, String> param = ReflectUtils.getObjAttr(message);
        if (param == null || param.size() == 0) {
            throw new IllegalArgumentException("error message");
        }

        StringBuilder body = null;
        for (Map.Entry<String, String> entry : param.entrySet()) {
            if (body == null) {
                body = new StringBuilder();
            }
            addParameter(body, entry.getKey(), entry.getValue());
        }

        Sender<PushResult> sender = new Sender<PushResult>();
        final StringBuilder finalBody = body;
        ResultPack<PushResult> resultPack = sender.execRetries(new ExecHttpApplication<PushResult>() {

            @Override
            public ResultPack<PushResult> request() throws IOException {

                HttpResult httpResult = post(_url, finalBody.toString());
                if (httpResult == null) {
                    return null;
                }

                String code = httpResult.getCode();
                String msg = httpResult.getMessage();
                String data = httpResult.getData();
                if (Constants.SUCCESS_CODE.equals(code)) {
                    PushResult respTarget = new PushResult();
                    if (StringUtils.isNotBlank(data)) {
                        respTarget = JSONObject.parseObject(data, PushResult.class);
                    }
                    return ResultPack.succeed(code, msg, respTarget);
                } else {
                    return ResultPack.failed(code, msg);
                }
            }

            /**
             * 刷新token
             *
             * @return true 正常  false接口响应失败
             * @throws InvalidAuthTokenException 接口响应token非法
             */
            @Override
            public boolean refreshToken() throws InvalidAuthTokenException {
                return OppoSender.this.refreshToken();
            }
        }, retries);

        if (resultPack.isSucceed()) {
            return ResultPack.succeed(resultPack.code(), resultPack.comment(), resultPack.value().getMessage_id());
        } else {
            return ResultPack.failed(resultPack.code(), resultPack.comment());
        }
    }

    /**
     * 广播推送-通知栏消息
     *
     * @param messageId
     * @param targetType
     * @param targets    推送目标用户【多个以英文分号(;)分隔，最大1000个】，可以替代 registration_id
     * @return
     * @throws IOException
     */
    public ResultPack<PushResult> notificationBroadcast(String messageId, TargetType targetType, Collection<String> targets) throws IOException {
        return notificationBroadcast(messageId, targetType, targets, 0);
    }


    public ResultPack<PushResult> notificationBroadcast(String messageId, TargetType targetType, Collection<String> targets, int retries) throws IOException {
        if (CollectionUtils.isEmpty(targets)) {
            return ResultPack.failed("targets is empty");
        }
        if (StringUtils.isBlank(messageId)) {
            return ResultPack.failed("messageId is null");
        }
        if (targetType == null) {
            return ResultPack.failed("targetType is null");
        }

        final String _url = Constants.NOTIFICATION_BROADCAST_URL;

        String targetValue = CollectionUtils.list2Str(targets);

        final StringBuilder body = newBody("message_id", messageId);
        addParameter(body, "target_type", String.valueOf(targetType.getType()));
        addParameter(body, "target_value", targetValue);

        Sender<PushResult> sender = new Sender<PushResult>();
        ResultPack<PushResult> resultPack = sender.execRetries(new ExecHttpApplication<PushResult>() {

            @Override
            public ResultPack<PushResult> request() throws IOException {

                HttpResult httpResult = post(_url, body.toString());
                if (httpResult == null) {
                    return null;
                }

                String code = httpResult.getCode();
                String msg = httpResult.getMessage();
                String data = httpResult.getData();
                if (Constants.SUCCESS_CODE.equals(code)) {
                    PushResult respTarget = new PushResult();
                    if (StringUtils.isNotBlank(data)) {
                        respTarget = JSONObject.parseObject(data, PushResult.class);
                        Map<String, Object> maps = JSONObject.parseObject(data, Map.class);
                        if (maps != null && maps.size() > 0) {
                            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                                String key = entry.getKey();
                                if (NumberUtils.isNumber(key)) {
                                    List<String> targets = (List<String>) entry.getValue();
                                    respTarget.result2Data(key, targets);
                                }
                            }
                        }
                    }
                    return ResultPack.succeed(code, msg, respTarget);
                } else {
                    return ResultPack.failed(code, msg);
                }
            }

            /**
             * 刷新token
             *
             * @return true 正常  false接口响应失败
             * @throws InvalidAuthTokenException 接口响应token非法
             */
            @Override
            public boolean refreshToken() throws InvalidAuthTokenException {
                return OppoSender.this.refreshToken();
            }
        }, retries);
        return resultPack;
    }

    /**
     * 批量单推-通知栏消息推送
     *
     * @param messages
     * @return
     * @throws IOException
     */
    public ResultPack<List<PushResult>> notificationUnicastBatch(List<PushMessage> messages) throws IOException {
        return this.notificationUnicastBatch(messages, 0);
    }

    /**
     * 批量单推-通知栏消息推送
     *
     * @param messages
     * @param retries
     * @return
     * @throws IOException
     */
    public ResultPack<List<PushResult>> notificationUnicastBatch(List<PushMessage> messages, int retries) throws IOException {
        if (CollectionUtils.isEmpty(messages)) {
            return ResultPack.failed("messages is empty");
        }

        final String _url = Constants.NOTIFICATION_UNICAST_BATCH_URL;

        final StringBuilder body;
        try {
            body = newBody("messages", JSON.toJSONString(messages));
        } catch (UnsupportedEncodingException e) {
            logger.error("messages to json str error", e);
            throw new IllegalArgumentException("messages to json str error");
        }

        Sender<List<PushResult>> sender = new Sender<List<PushResult>>();
        ResultPack<List<PushResult>> resultPack = sender.execRetries(new ExecHttpApplication<List<PushResult>>() {

            @Override
            public ResultPack<List<PushResult>> request() throws IOException {

                HttpResult httpResult = post(_url, body.toString());
                if (httpResult == null) {
                    return null;
                }

                String code = httpResult.getCode();
                String msg = httpResult.getMessage();
                String data = httpResult.getData();

                if (Constants.SUCCESS_CODE.equals(code)) {
                    List<PushResult> respTarget = new ArrayList<PushResult>();
                    if (StringUtils.isNotBlank(data)) {
                        respTarget = JSONObject.parseArray(data, PushResult.class);
                    }
                    return ResultPack.succeed(code, msg, respTarget);
                } else {
                    return ResultPack.failed(code, msg);
                }
            }

            /**
             * 刷新token
             *
             * @return true 正常  false接口响应失败
             * @throws InvalidAuthTokenException 接口响应token非法
             */
            @Override
            public boolean refreshToken() throws InvalidAuthTokenException {
                return OppoSender.this.refreshToken();
            }
        }, retries);
        return resultPack;
    }


    /**
     * 单推-通知栏消息推送
     *
     * @param message
     * @param targets
     * @return
     * @throws IOException
     */
    public ResultPack<PushResult> notificationUnicast(TargetType targetType, OppoMessage message, String targets) throws IOException {
        return this.notificationUnicast(targetType, message, targets, 0);
    }

    /**
     * 单推-通知栏消息推送
     *
     * @param targetType
     * @param message
     * @param targets
     * @param retries
     * @return
     * @throws IOException
     */
    public ResultPack<PushResult> notificationUnicast(TargetType targetType, OppoMessage message, String targets, int retries) throws IOException {
        if (StringUtils.isEmpty(targets)) {
            return ResultPack.failed("targets is empty");
        }
        if (message == null) {
            return ResultPack.failed("message is null");
        }

        final String _url = Constants.NOTIFICATION_UNICAST_URL;

        PushMessage msg = new PushMessage(String.valueOf(targetType.getType()), targets, message);
        final StringBuilder body = newBody("message", JSON.toJSONString(msg));

        Sender<PushResult> sender = new Sender<PushResult>();


        ResultPack<PushResult> resultPack = sender.execRetries(new ExecHttpApplication<PushResult>() {

            @Override
            public ResultPack<PushResult> request() throws IOException {

                HttpResult httpResult = post(_url, body.toString());
                if (httpResult == null) {
                    return null;
                }

                String code = httpResult.getCode();
                String msg = httpResult.getMessage();
                String data = httpResult.getData();
                if (Constants.SUCCESS_CODE.equals(code)) {
                    PushResult respTarget = new PushResult();
                    if (StringUtils.isNotBlank(data)) {
                        respTarget = JSONObject.parseObject(data, PushResult.class);
                    }
                    return ResultPack.succeed(code, msg, respTarget);
                } else {
                    return ResultPack.failed(code, msg);
                }
            }

            @Override
            public boolean refreshToken() throws InvalidAuthTokenException {
                return OppoSender.this.refreshToken();

            }
        }, retries);

        return resultPack;
    }

    private boolean refreshToken() throws InvalidAuthTokenException {
        //token失效重新获取
        String key = UpsUtils.getTokenKey(appKey);
        TokenSender tokenSender = tokens.get(key);
        if (tokenSender == null) {
            tokenSender = new TokenSender(appKey, masterSecret);
            tokens.put(key, tokenSender);
        } else {
            try {
                tokenSender.flushToken(appKey, masterSecret, 3);
            } catch (IOException e) {
                logger.error("flushToken error", e);
                return false;
            }
        }
        //重置token
        super.setAuthToken(tokenSender.getToken());
        return true;
    }


    /**
     * 广播类推送统计（message/statistics）
     *
     * @param messageId
     * @param taskId
     * @return
     * @throws IOException
     */
    public ResultPack<Statistics> statistics(String messageId, String taskId) throws IOException {
        return this.statistics(messageId, taskId, 0);
    }

    /**
     * 广播类推送统计（message/statistics）
     *
     * @param messageId
     * @param taskId
     * @param retries
     * @return
     * @throws IOException
     */
    public ResultPack<Statistics> statistics(String messageId, String taskId, int retries) throws IOException {

        final String _url = Constants.STATISTICS_URL;

        final StringBuilder body = newBody("message_id", messageId);
        addParameter(body, "task_id", taskId);

        Sender<Statistics> sender = new Sender<Statistics>();
        ResultPack<Statistics> resultPack = sender.execRetries(new ExecHttpApplication<Statistics>() {

            @Override
            public ResultPack<Statistics> request() throws IOException {

                HttpResult httpResult = post(_url, body.toString());
                if (httpResult == null) {
                    return null;
                }

                String code = httpResult.getCode();
                String msg = httpResult.getMessage();
                String data = httpResult.getData();
                if (Constants.SUCCESS_CODE.equals(code)) {
                    Statistics respTarget = new Statistics();
                    if (StringUtils.isNotBlank(data)) {
                        respTarget = JSONObject.parseObject(data, Statistics.class);
                    }
                    return ResultPack.succeed(code, msg, respTarget);
                } else {
                    return ResultPack.failed(code, msg);
                }
            }

            /**
             * 刷新token
             *
             * @return true 正常  false接口响应失败
             * @throws InvalidAuthTokenException 接口响应token非法
             */
            @Override
            public boolean refreshToken() throws InvalidAuthTokenException {
                return OppoSender.this.refreshToken();
            }
        }, retries);
        return resultPack;
    }
}
