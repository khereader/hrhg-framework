/**
 * 简单通用业务逻辑。<br>
 */
package com.integrity.framework.srv.blogic;

/**
 * 简单通用业务逻辑。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class ParamBLogic<P extends Object, R extends Object> extends AbstractSimpleBLogic<P, R> {
    /**
     * 获取排序字段信息。<br>
     *
     * @return 排序字段信息
     */
    @Override
    protected String orderField() {
        return null;
    }

    /**
     * 获取分组字段信息。<br>
     *
     * @return 分组字段信息
     */
    @Override
    protected String groupField() {
        return null;
    }
}
