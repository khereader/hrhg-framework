/**
 * 业务逻辑异常。<br>
 */
package com.hrhg.framework.exception;

import com.hrhg.framework.code.CodeMessage;

/**
 * 业务逻辑异常。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public class BLogicException extends Exception {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -4727896206963143427L;

    /**
     * 错误编码
     */
    private String code;
    /**
     * 内部异常信息
     */
    private Exception exInner;

    /**
     * 构造函数。<br>
     *
     * @param codeMessage 错误编码
     * @param args        异常参数
     */
    public BLogicException(CodeMessage codeMessage, Object... args) {
        this(null, codeMessage, args);
    }

    /**
     * 构造函数。<br>
     *
     * @param code      错误编码
     * @param msgFormat 消息格式
     * @param args      异常参数
     */
    public BLogicException(String code, String msgFormat, Object... args) {
        this(null, code, msgFormat, args);
    }

    /**
     * 构造函数。<br>
     *
     * @param exInner     内部异常
     * @param codeMessage 错误编码
     * @param args        异常参数
     */
    public BLogicException(Exception exInner, CodeMessage codeMessage, Object... args) {
        this(exInner, codeMessage.getFullCode(), codeMessage.getMessage(), args);
    }

    /**
     * 构造函数。<br>
     *
     * @param exInner   内部异常
     * @param code      错误编码
     * @param msgFormat 消息格式
     * @param args      异常参数
     */
    public BLogicException(Exception exInner, String code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args), exInner);
        this.code = code;
        this.exInner = exInner;
    }

    /**
     * 获取错误编码。<br>
     *
     * @return 错误编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取内部异常信息。<br>
     *
     * @return 内部异常信息
     */
    public Exception getExInner() {
        return exInner;
    }
}
