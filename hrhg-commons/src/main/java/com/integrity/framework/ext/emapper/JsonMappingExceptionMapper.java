/**
 * 远程服务错误。<br>
 */
package com.integrity.framework.ext.emapper;

import com.integrity.framework.api.bean.BodyBaseResp;
import com.integrity.framework.api.bean.DefaultResp;
import com.integrity.framework.api.code.SysCode;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 远程服务错误。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public class JsonMappingExceptionMapper extends BaseExceptionMapper<JsonMappingException> {
    /**
     * 生成异常响应消息。<br>
     *
     * @param ex 异常类型
     * @return 响应对象
     */
    @Override
    protected BodyBaseResp<?> makeResp(JsonMappingException ex) {
        DefaultResp resp = new DefaultResp();
        resp.getHead().setResult(SysCode.Message.E_PARAM_FORMAT.getFullCode());
        resp.getHead().setMsg(SysCode.Message.E_PARAM_FORMAT.getMessage());
        return resp;
    }
}
