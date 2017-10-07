/**
 * 登录用户信息接口。<br>
 */
package com.integrity.framework.srv.blogic;

/**
 * 登录用户信息接口。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface SimpleWithAuthBLogic<P extends Object, R extends Object> extends BaseBLogic<P, R> {
    /**
     * 获取登录用户ID。<br>
     *
     * @return 登录用户ID
     */
    String getUidLogin();

    /**
     * 设置登录用户ID。<br>
     *
     * @param uidLogin 登录用户ID
     */
    void setUidLogin(String uidLogin);
}
