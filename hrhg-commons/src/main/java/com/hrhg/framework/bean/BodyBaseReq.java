/**
 * 服务消息体基类请求参数。<br>
 */
package com.hrhg.framework.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务消息体基类请求参数。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BodyBaseReq<B extends BodyReq> extends Request<HeadReq, B> {
    /**
     * 创建消息头。<br>
     */
    @Override
    protected void createHead() {
        this.head = new HeadReq();
    }
}
