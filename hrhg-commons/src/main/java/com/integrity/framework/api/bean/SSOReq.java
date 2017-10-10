/**
 * 单点登录鉴权请求参数。<br>
 */
package com.integrity.framework.api.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 单点登录鉴权请求参数。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SsoReq extends BodyBaseReq<SsoBodyReq> {
    /**
     * 创建消息体。<br>
     */
    @Override
    protected void createBody() {
        this.body = new SsoBodyReq();
    }
}
