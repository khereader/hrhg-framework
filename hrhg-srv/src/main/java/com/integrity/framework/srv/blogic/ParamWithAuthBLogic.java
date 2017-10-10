/**
 * 简单通用业务逻辑。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.api.code.CodePath;
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
public abstract class ParamWithAuthBLogic<P extends Object, R extends Object>
        extends ParamBLogic<P, R> implements SimpleWithAuthBLogic<P, R> {
    /**
     * 登录用户ID
     */
    protected String uidLogin;

    /**
     * 更新鉴权信息。<br>
     *
     * @param uid      用户ID
     * @param codePath 编码路径
     */
    @Override
    public void refreshAuthInfo(String uid, CodePath codePath) {
        this.uidLogin = uid;
    }
}
