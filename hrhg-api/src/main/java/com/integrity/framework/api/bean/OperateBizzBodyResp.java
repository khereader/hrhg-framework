/**
 * 业务操作控制响应消息体。<br>
 */
package com.integrity.framework.api.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务操作控制响应消息体。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OperateBizzBodyResp extends BodyResp {
    /**
     * 处理成功业务个数
     */
    private int cnt;
}
