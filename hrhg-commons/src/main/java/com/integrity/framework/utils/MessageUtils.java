/**
 * 阿里消息服务工具。<br>
 */
package com.integrity.framework.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.util.List;

/**
 * 阿里消息服务工具。<br>
 *
 * @author 李海军
 */
public final class MessageUtils {
    /**
     * 短信连接超时属性名
     */
    public static String SMS_PROPERTY_CONNECT_TIMEOUT = "sun.net.client.defaultConnectTimeout";
    /**
     * 短信连接超时值
     */
    public static String SMS_VALUE_CONNECT_TIMEOUT = "10000";
    /**
     * 短信读取超时属性名
     */
    public static String SMS_PROPERTY_READ_TIMEOUT = "sun.net.client.defaultReadTimeout";
    /**
     * 短信读取超时值
     */
    public static String SMS_VALUE_READ_TIMEOUT = "10000";
    /**
     * 短信API产品名称
     */
    public static String SMS_API_PRODUCT_NAME = "Dysmsapi";
    /**
     * 短信API产品域名
     */
    public static String SMS_API_DOMAIN = "dysmsapi.aliyuncs.com";
    /**
     * 短信API区域ID
     */
    public static String SMS_API_REGION_ID = "cn-hangzhou";
    /**
     * 短信API接入点
     */
    public static String SMS_API_END_POINT_NAME = "cn-hangzhou";
    /**
     * 短信API接入点
     */
    public static String SMS_API_RESPONSE_CODE = "OK";

    /**
     * 发送短信服务。<br>
     *
     * @param accessKeyId     访问ID
     * @param accessKeySecret 访问安全码
     * @param freeSignName    短信签名
     * @param templateCode    短信消息模板ID
     * @param paramBean       短信模板参数
     * @param phones          短信发送手机号集合
     * @return 返回消息ID
     * @throws Exception 异常信息
     */
    public static String sendSMSMessage(String accessKeyId,
                                        String accessKeySecret,
                                        String freeSignName,
                                        String templateCode,
                                        Object paramBean,
                                        List<String> phones) throws Exception {
        return sendSMSMessage(accessKeyId, accessKeySecret, freeSignName, templateCode, paramBean,
                DataUtils.isNullOrEmpty(phones) ? null : phones.toArray(new String[phones.size()]));
    }

    /**
     * 发送短信服务。<br>
     *
     * @param accessKeyId     访问ID
     * @param accessKeySecret 访问安全码
     * @param freeSignName    短信签名
     * @param templateCode    短信消息模板ID
     * @param paramBean       短信模板参数bean
     * @param phones          短信发送手机号集合
     * @return 返回消息ID
     * @throws Exception 异常信息
     */
    public static String sendSMSMessage(String accessKeyId,
                                        String accessKeySecret,
                                        String freeSignName,
                                        String templateCode,
                                        Object paramBean,
                                        String... phones) throws Exception {

        return sendSMSMessage(accessKeyId, accessKeySecret, freeSignName, templateCode,
                JsonUtils.object2Json(paramBean), StringUtils.makeSingleQuotation(phones));
    }

    /**
     * 发送短信服务。<br>
     *
     * @param accessKeyId     访问ID
     * @param accessKeySecret 访问安全码
     * @param freeSignName    短信签名
     * @param templateCode    短信消息模板ID
     * @param paramJson       短信模板参数
     * @param phoneComma      短信发送逗号分隔手机号集合
     * @return 返回消息ID
     * @throws Exception 异常信息
     */
    public static String sendSMSMessage(String accessKeyId,
                                        String accessKeySecret,
                                        String freeSignName,
                                        String templateCode,
                                        String paramJson,
                                        String phoneComma) throws Exception {
        if (StringUtils.isEmpty(accessKeyId) || StringUtils.isEmpty(accessKeySecret) ||
                StringUtils.isEmpty(freeSignName) || StringUtils.isEmpty(templateCode) ||
                StringUtils.isEmpty(paramJson) || StringUtils.isEmpty(phoneComma)) {
            // 参数检查失败
            return StringUtils.EMPTY_STRING;
        }

        // 业务编码
        String bizCode = StringUtils.NULL_STRING;
        // 设置超时时间
        // 连接超时时间
        System.setProperty(SMS_PROPERTY_CONNECT_TIMEOUT, SMS_VALUE_CONNECT_TIMEOUT);
        // 读取超时时间
        System.setProperty(SMS_PROPERTY_READ_TIMEOUT, SMS_VALUE_READ_TIMEOUT);
        // 初始化ascClient需要的几个参数
        // 短信API产品名称（短信产品名固定，无需修改）
        final String product = SMS_API_PRODUCT_NAME;
        // 短信API产品域名（接口地址固定，无需修改）
        final String domain = SMS_API_DOMAIN;

        // 初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile(SMS_API_REGION_ID, accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint(SMS_API_END_POINT_NAME, SMS_API_REGION_ID, product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        // 组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        // 使用post提交
        request.setMethod(MethodType.POST);
        // 必填:待发送手机号。
        // 支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,
        // 批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(phoneComma);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(freeSignName);
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况
        // 在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam(paramJson);
        // 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        // request.setSmsUpExtendCode("90997");
        // 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        String outId = String.valueOf(DateUtils.getTimeStamp());
        request.setOutId(outId);
        // 请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        if (!DataUtils.isNullOrEmpty(sendSmsResponse.getCode()) &&
                StringUtils.isEquals(sendSmsResponse.getCode(), SMS_API_RESPONSE_CODE)) {
            return sendSmsResponse.getBizId();
        }

        return sendSmsResponse.getBizId();
    }
}


