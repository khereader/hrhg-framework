/**
 * 消息头基类。<br>
 */
package com.hrhg.framework.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 消息头基类。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
public abstract class Head implements Serializable {
    /**
     * 请求时间（时间戳）
     */
    private Long time;
    /**
     * 请求流水号（time＋IMEI）
     */
    @JsonIgnore
    private String serial;
    /**
     * 消息头业务编码
     */
    @JsonIgnore
    private String code;
    /**
     * 消息版本
     */
    @NotNull
    private String ver = "1.0.0";
    /**
     * 消息令牌（请求消息头的验证令牌；响应消息头的服务端回写令牌）
     * 请求参数时，用户名、密码信息请求时，优先用户名、密码验证
     */
    private String token;
}
