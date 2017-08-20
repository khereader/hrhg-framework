/**
 * 远程服务错误。<br>
 */
package com.hrhg.framework.ext.emapper;

import com.hrhg.framework.bean.BodyBaseResp;
import com.hrhg.framework.bean.DefaultResp;
import com.hrhg.framework.code.SysCode;
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
