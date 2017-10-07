/**
 * 编码消息类型接口。<br>
 */
package com.integrity.framework.api.code;

/**
 * 编码消息类型接口。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface CodeMessage extends CodeType {
    /**
     * 消息前缀
     */
    String HEAD_MESSAGE = "MESSAGE";
    /**
     * 编码格式化_消息编码
     */
    String FORMAT_CODE_MESSAGE = "%02d";

    /**
     * 获取消息。<br>
     *
     * @return 消息
     */
    String getMessage();
}
