/**
 * 业务操作控制响应参数。<br>
 */
package com.integrity.framework.api.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务操作控制响应参数。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OperateBizzResp extends BodyBaseResp<OperateBizzBodyResp> {
    /**
     * 创建消息体对象。<br>
     */
    @Override
    protected void createBody() {
        this.body = new OperateBizzBodyResp();
    }
}
