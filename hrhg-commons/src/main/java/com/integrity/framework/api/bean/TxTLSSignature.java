package com.integrity.framework.api.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 腾讯云通讯签名参数<dr>
 *
 * @author 姚磊
 * @time 2017/12/21
 * @since 2.1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TxTLSSignature implements Serializable {
    /**
     * 独立模式 及腾讯云AppId
     */
    @JsonProperty(value = "TLS.appid_at_3rd")
    private String appIdAtThird;
    /**
     * 签名过期时间
     */
    @JsonProperty(value = "TLS.expire_after")
    private String expireAfter;
    /**
     * 账号管理员 云通讯后台管理查询
     */
    @JsonProperty(value = "TLS.identifier")
    private String identifier;
    /**
     * 签名
     */
    @JsonProperty(value = "TLS.sig")
    private String sig;
    /**
     * 腾讯云AppId 云通讯后台管理查询
     */
    @JsonProperty(value = "TLS.sdk_appid")
    private String sdkAppId;
    /**
     * 签名生成时间
     */
    @JsonProperty(value = "TLS.time")
    private String time;
    /**
     * 账号类型 云通讯后台管理查询
     */
    @JsonProperty(value = "TLS.account_type")
    private String accountType;
}
