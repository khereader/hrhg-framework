/**
 * 面向切面通用业务逻辑。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.api.bean.BodyBaseReq;
import com.integrity.framework.api.bean.BodyBaseResp;

/**
 * 面向切面通用业务逻辑。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class AopBaseBLogic<P extends BodyBaseReq, R extends BodyBaseResp> extends AopPageBLogic<P, R> {
    /**
     * 获取排序字段信息。<br>
     *
     * @return 排序字段信息
     */
    @Override
    protected String orderField() {
        return null;
    }
}
