package com.integrity.framework.api.bean;

import com.integrity.framework.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 腾讯云通讯签名验证结果参数<dr>
 *
 * @author 姚磊
 * @time 2017/12/21
 * @since 2.1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TxCheckTLSSignatureResult implements Serializable {
    /**
     * 签名验证错误信息
     */
    private String errMessage;
    /**
     * 签名验证结果
     */
    private boolean verifyResult;
    /**
     * 签名失效时间
     */
    private int expireTime;
    /**
     * 签名失效时间
     */
    private int initTime;

    public TxCheckTLSSignatureResult() {
        errMessage = StringUtils.NULL_STRING;
        verifyResult = Boolean.FALSE;
    }
}
