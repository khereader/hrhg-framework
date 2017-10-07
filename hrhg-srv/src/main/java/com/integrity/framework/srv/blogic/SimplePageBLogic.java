/**
 * 简单通用业务逻辑。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.api.bean.BodyReq;
import com.integrity.framework.api.bean.BodyResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 简单通用业务逻辑。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class SimplePageBLogic<P extends BodyReq, R extends BodyResp>
        extends AbstractSimpleBLogic<P, R> implements SimpleWithAuthBLogic<P, R> {
    /**
     * 登录用户ID
     */
    protected String uidLogin;

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
