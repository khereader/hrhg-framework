/**
 * 远程服务错误。<br>
 */
package com.hrhg.framework.ext.emapper;

import com.hrhg.framework.bean.BodyBaseResp;
import com.hrhg.framework.bean.DefaultResp;
import com.hrhg.framework.code.SysCode;
import com.fasterxml.jackson.core.JsonParseException;

/**
 * 远程服务错误。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public class JsonParseExceptionMapper extends BaseExceptionMapper<JsonParseException> {
    /**
     * 生成异常响应消息。<br>
     *
     * @param ex 异常类型
     * @return 响应对象
     */
    @Override
    protected BodyBaseResp<?> makeResp(JsonParseException ex) {
        DefaultResp resp = new DefaultResp();
        resp.getHead().setResult(SysCode.Message.E_PARAM_PARSE.getFullCode());
        resp.getHead().setMsg(SysCode.Message.E_PARAM_PARSE.getMessage());
        return resp;
    }
}
