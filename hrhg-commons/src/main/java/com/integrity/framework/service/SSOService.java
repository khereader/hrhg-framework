/**
 * 单点登录鉴权服务。<br>
 */
package com.integrity.framework.service;

import com.integrity.framework.api.bean.SSOReq;
import com.integrity.framework.api.bean.SSOResp;
import com.integrity.framework.exception.RespException;

/**
 * 单点登录鉴权服务。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface SSOService {
    /**
     * 单点登录鉴权服务接口。<br>
     *
     * @param req 单点登录鉴权请求参数
     * @return 单点登录鉴权响应参数
     * @throws RespException 业务逻辑异常
     */
    SSOResp sso(SSOReq req) throws RespException;
}