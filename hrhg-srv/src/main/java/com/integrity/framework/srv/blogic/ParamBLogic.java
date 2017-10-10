/**
 * 简单通用业务逻辑。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.api.code.CodePath;

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
    
    /**
     * 更新鉴权信息。<br>
     *
     * @param uid      用户ID
     * @param codePath 编码路径
     */
    @Override
    public void refreshAuthInfo(String uid, CodePath codePath) {
    }
}
