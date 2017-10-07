/**
 * 请求参数错误。<br>
 */
package com.integrity.framework.ext.emapper;

import com.integrity.framework.api.bean.BodyBaseResp;
import com.integrity.framework.api.bean.DefaultResp;
import com.integrity.framework.api.code.SysCode;

import javax.ws.rs.NotFoundException;

/**
 * 请求参数错误。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public class NotFoundExceptionMapper extends BaseExceptionMapper<NotFoundException> {
    /**
     * 生成异常响应消息。<br>
     *
     * @param ex 异常类型
     * @return 响应对象
     */
    @Override
    protected BodyBaseResp<?> makeResp(NotFoundException ex) {
        DefaultResp resp = new DefaultResp();
        resp.getHead().setResult(SysCode.Message.E_NOT_FOUND.getFullCode());
        resp.getHead().setMsg(SysCode.Message.E_NOT_FOUND.getMessage());
        return resp;
    }
}
