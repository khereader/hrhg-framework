package com.integrity.framework.api.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 腾讯云通讯TLS 生成签名结果参数<dr>
 *
 * @author 姚磊
 * @time 2017/12/21
 * @since 2.1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TxGenTLSSignatureResult implements Serializable{
    /**
     * 签名失败信息
     */
    private String errMessage;
    /**
     * 签名
     */
    private String urlSig;
    /**
     * 失效时间 单位秒
     */
    private int expireTime;
    /**
     * 创建时间 时间戳
     */
    private int initTime;

    public TxGenTLSSignatureResult() {
        errMessage = "";
        urlSig = "";
    }
}
