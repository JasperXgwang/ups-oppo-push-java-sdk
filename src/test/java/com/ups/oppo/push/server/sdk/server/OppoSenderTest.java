package com.ups.oppo.push.server.sdk.server;


import com.alibaba.fastjson.JSON;
import com.ups.oppo.push.server.sdk.common.OppoClieckType;
import com.ups.oppo.push.server.sdk.common.TargetType;
import com.ups.oppo.push.server.sdk.model.OppoMessage;
import com.ups.oppo.push.server.sdk.util.ResultPack;
import com.ups.oppo.push.server.sdk.vo.PushMessage;
import com.ups.oppo.push.server.sdk.vo.PushResult;
import com.ups.oppo.push.server.sdk.vo.Statistics;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxinguo
 * @date 2018-5-30
 */
public class OppoSenderTest {

    private static final Logger logger = LoggerFactory.getLogger(OppoSenderTest.class);

    private static final String appKey = "appKey";
    private static final String masterSecret = "masterSecret";


    /**
     * 单推-通知栏消息推送（message/notification/unicast）
     *
     * @throws Exception
     */
    @Test
    public void notificationUnicast() throws Exception {

        OppoSender sender = new OppoSender(appKey, masterSecret);
        OppoMessage message = new OppoMessage()
                .setTitle("java sdk title:" + System.currentTimeMillis())
                .setContent("java sdk push")
                .setClick_action_type(OppoClieckType.ACTIVITY.getDesc())
                .setClick_action_activity("com.meizu.upspushdemo.TestActivty")
                .setClick_action_url("https://push.meizu.com")
                .setAction_parameters(JSON.parseObject("{\"key1\":\"value1\",\"key2\":\"value2\"}"));

        String targets = "CN_72b8c9365b8ce837072dddc3dae2a90e";

        ResultPack<PushResult> resultPack = sender.notificationUnicast(TargetType.REG_ID, message, targets);
        getResult(resultPack);
    }


    /**
     * 批量单推-通知栏消息推送(message/notification/unicast_batch）
     *
     * @throws IOException
     */
    @Test
    public void notificationUnicastBatch() throws IOException {
        OppoSender sender = new OppoSender(appKey, masterSecret);

        OppoMessage message = new OppoMessage()
                .setTitle("java sdk")
                .setContent("java sdk push");

        String target1 = "CN_72b8c9365b8ce837072dddc3dae2a90e";
        String target2 = "CN_2617084a1d8acf5800873480b4528a5b";
        PushMessage msg1 = new PushMessage(String.valueOf(TargetType.REG_ID.getType()), target1, message);
        PushMessage msg2 = new PushMessage(String.valueOf(TargetType.REG_ID.getType()), target2, message);
        List<PushMessage> messages = new ArrayList<PushMessage>();
        messages.add(msg1);
        messages.add(msg2);

        ResultPack<List<PushResult>> resultPack = sender.notificationUnicastBatch(messages);
        getResult(resultPack);
    }

    /**
     * 保存通知栏消息内容体（message/notification/save_message_content）
     *
     * @throws IOException
     */
    @Test
    public void saveMessageContent() throws IOException {
        OppoSender sender = new OppoSender(appKey, masterSecret);

        OppoMessage message = new OppoMessage()
                .setTitle("java sdk saveMessageContent")
                .setContent("java sdk push")
                .setAction_parameters(JSON.parseObject("{\"key1\":\"value1\",\"key2\":\"value2\"}"));

        ResultPack<String> resultPack = sender.saveMessageContent(message);
        getResult(resultPack);
    }

    /**
     * 广播推送-通知栏消息(message/notification/broadcast)
     *
     * @throws IOException
     */
    @Test
    public void notificationBroadcast() throws IOException {
        OppoSender sender = new OppoSender(appKey, masterSecret);

        String messageId = "5b0f9c8d3e80e97da0e0ebde";

        List<String> targets = new ArrayList<String>();
        targets.add("CN_72b8c9365b8ce837072dddc3dae2a90e");
        targets.add("CN_2617084a1d8acf5800873480b4528a5b");
//        for (int i = 0; i < 50; i++) {
//            targets.add("CN_" + i);
//        }

        ResultPack<PushResult> resultPack = sender.notificationBroadcast(messageId, TargetType.REG_ID, targets);
        getResult(resultPack);
    }

    /**
     * 广播类推送统计（message/statistics）
     *
     * @throws IOException
     */
    @Test
    public void statistics() throws IOException {
        OppoSender sender = new OppoSender(appKey, masterSecret);
        String messageId = "5b0f9c8d3e80e97da0e0ebde";
        String taskId = "5b0fa36ca609e97b6d92f7fb";
        ResultPack<Statistics> resultPack = sender.statistics(messageId, taskId);
        getResult(resultPack);
    }


    private void getResult(ResultPack resultPack) {
        if (resultPack.isSucceed()) {
            logger.info("resultPack:{}", resultPack);
        } else {
            logger.error("resultPack:{}", resultPack);
        }
    }
}