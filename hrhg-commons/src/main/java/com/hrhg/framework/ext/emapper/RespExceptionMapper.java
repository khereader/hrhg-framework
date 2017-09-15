/**
 * 业务响应异常。<br>
 */
package com.hrhg.framework.ext.emapper;

import com.hrhg.framework.bean.BodyBaseResp;
import com.hrhg.framework.exception.RespException;

/**
 * 业务响应异常。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public class RespExceptionMapper extends BaseExceptionMapper<RespException> {
    /**
     * 生成异常响应消息。<br>
     *
     * @param ex 异常类型
     * @return 响应对象
     */
    @Override
    protected BodyBaseResp<?> makeResp(RespException ex) {
        // 业务逻辑异常
        return ex.getResp();
    }
}
