/**
 * 简单通用业务逻辑。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.api.bean.BodyReq;
import com.integrity.framework.api.bean.BodyResp;

/**
 * 简单通用业务逻辑。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class SimpleBaseBLogic<P extends BodyReq, R extends BodyResp> extends SimplePageBLogic<P, R> {
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
