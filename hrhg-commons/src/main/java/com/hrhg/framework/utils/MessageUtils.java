/**
 * 阿里消息服务工具。<br>
 */
package com.hrhg.framework.utils;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;

import java.util.List;
import java.util.Map;

/**
 * 阿里消息服务工具。<br>
 *
 * @author 李海军
 */
public final class MessageUtils {
    /**
     * 短信消息体内容
     */
    public static String MESSAGE_BODY_SMS = "sms-message";

    /**
     * 发送短信服务。<br>
     *
     * @param accessKeyId     访问ID
     * @param accessKeySecret 访问安全码
     * @param accountEndpoint 访问目标地址
     * @param topicName       主题名
     * @param freeSignName    短信签名
     * @param templateCode    短信消息模板ID
     * @param phones          短信发送手机号集合
     * @param paramBean       短信模板参数bean
     * @return 返回消息ID
     * @throws Exception 异常信息
     */
    public static String sendSMSMessage(String accessKeyId,
                                        String accessKeySecret,
                                        String accountEndpoint,
                                        String topicName,
                                        String freeSignName,
                                        String templateCode,
                                        List<String> phones,
                                        Object paramBean) throws Exception {
        return sendSMSMessage(accessKeyId, accessKeySecret, accountEndpoint, topicName,
                freeSignName, templateCode, phones, BeanUtils.beanToMap(paramBean));
    }

    /**
     * 发送短信服务。<br>
     *
     * @param accessKeyId     访问ID
     * @param accessKeySecret 访问安全码
     * @param accountEndpoint 访问目标地址
     * @param topicName       主题名
     * @param freeSignName    短信签名
     * @param templateCode    短信消息模板ID
     * @param phones          短信发送手机号集合
     * @param params          短信模板参数
     * @return 返回消息ID
     * @throws Exception 异常信息
     */
    public static String sendSMSMessage(String accessKeyId,
                                        String accessKeySecret,
                                        String accountEndpoint,
                                        String topicName,
                                        String freeSignName,
                                        String templateCode,
                                        List<String> phones,
                                        Map<String, String> params) throws Exception {
        if (StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(accessKeySecret) ||
                StringUtils.isEmpty(accountEndpoint) || StringUtils.isEmpty(topicName) ||
                StringUtils.isEmpty(freeSignName) || StringUtils.isEmpty(templateCode) ||
                DataUtils.isNullOrEmpty(phones)) {
            // 参数检查失败
            return StringUtils.EMPTY_STRING;
        }

        /**
         * Step 1. 获取主题引用
         */
        CloudAccount account = new CloudAccount(accessKeyId, accessKeySecret, accountEndpoint);
        // 获取阿里服务客户端
        MNSClient client = account.getMNSClient();
        // 获取主题
        CloudTopic topic = client.getTopicRef(topicName);

        /**
         * Step 2. 设置SMS消息体（必须）
         *
         * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
         */
        RawTopicMessage msg = new RawTopicMessage();
        msg.setMessageBody(MESSAGE_BODY_SMS);

        /**
         * Step 3. 生成SMS消息属性
         */
        MessageAttributes messageAttributes = new MessageAttributes();
        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
        // 3.1 设置发送短信的签名（SMSSignName）
        batchSmsAttributes.setFreeSignName(freeSignName);
        // 3.2 设置发送短信使用的模板（SMSTempateCode）
        batchSmsAttributes.setTemplateCode(templateCode);

        // 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();

        if (!DataUtils.isNullOrEmpty(params)) {
            // 消息模版参数
            for (String key : params.keySet()) {
                // 逐个设置参数
                smsReceiverParams.setParam(key, params.get(key));
            }
        }

        for (String phone : phones) {
            // 3.4 增加接收短信的号码
            batchSmsAttributes.addSmsReceiver(phone, smsReceiverParams);
        }

        // 设置批量发送消息对象
        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);

        try {
            /**
             * Step 4. 发布SMS消息
             */
            TopicMessage ret = topic.publishMessage(msg, messageAttributes);
            // 返回消息ID
            return ret.getMessageId();
        } catch (Exception e) {
            throw e;
        } finally {
            client.close();
        }
    }
}
