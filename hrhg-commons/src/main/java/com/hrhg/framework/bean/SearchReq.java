/**
 * 查询字段请求参数。<br>
 */
package com.hrhg.framework.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查询字段请求参数。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchReq extends BodyBaseReq<SearchBodyReq> {
    /**
     * 创建消息体。<br>
     */
    @Override
    protected void createBody() {
        this.body = new SearchBodyReq();
    }
}
