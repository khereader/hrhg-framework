/**
 * 远程服务错误。<br>
 */
package com.integrity.framework.ext.emapper;

import com.alibaba.dubbo.rpc.RpcException;
import com.integrity.framework.api.bean.BodyBaseResp;
import com.integrity.framework.api.bean.DefaultResp;
import com.integrity.framework.api.code.FrameworkCode;

/**
 * 远程服务错误。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public class RpcExceptionMapper extends BaseExceptionMapper<RpcException> {
    /**
     * 生成异常响应消息。<br>
     *
     * @param ex 异常类型
     * @return 响应对象
     */
    @Override
    protected BodyBaseResp<?> makeResp(RpcException ex) {
        DefaultResp resp = new DefaultResp();
        resp.getHead().setResult(FrameworkCode.Message.E_RPC_RESOURCE.getFullCode());
        resp.getHead().setMsg(FrameworkCode.Message.E_RPC_RESOURCE.getMessage());
        return resp;
    }
}
