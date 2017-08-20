/**
 * 默认请求消息体。<br>
 */
package com.hrhg.framework.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 默认请求消息体。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DefaultBodyReq extends BodyReq {
    /**
     * 默认请求字段数据
     */
    private String req;
}
