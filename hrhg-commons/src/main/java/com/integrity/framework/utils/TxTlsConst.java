package com.integrity.framework.utils;

/**
 * <dr>
 *
 * @author 姚磊
 * @time 2017/12/22
 * @since 2.1.0
 */
public interface TxTlsConst {
    /**
     * SHA256withECDSA
     */
    String CODE_SHA256_WITH_ECDSA = "SHA256withECDSA";
    /**
     * BC
     */
    String CODE_BC = "BC";
    /**
     * UTF-8
     */
    String CODE_UTF_8 = "UTF-8";
    /**
     * 错误码 生成签名失败
     */
    String ERROR_GENERATE_USERSIG_FAILED = "生成签名失败";
    /**
     * 错误码 签名过期
     */
    String ERROR_SIG_OUT_OF_DATE = "签名过期";
    /**
     * 错误码 校验签名失败
     */
    String ERROR_CHECKING_SIG_FAIL = "校验签名失败";
    /**
     * 错误码 签名的APPID与待校验的不同
     */
    String ERROR_TLS_SIG_NOT_EQUAL_SDK_APP_ID = "签名的APPID{%s}与待校验{%s}不同";
}
