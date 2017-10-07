/**
 * 业务响应异常。<br>
 */
package com.integrity.framework.exception;

import com.integrity.framework.api.bean.BodyBaseReq;
import com.integrity.framework.api.bean.BodyBaseResp;

/**
 * 业务逻辑异常。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public class RespException extends Exception {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -4727896206963143427L;

    /**
     * 默认请求参数
     */
    private BodyBaseReq<?> req;
    /**
     * 默认响应对象
     */
    private BodyBaseResp<?> resp;
    /**
     * 内部异常信息
     */
    private BLogicException exInner;

    /**
     * 带参构造函数。<br>
     *
     * @param req     请求参数
     * @param exInner 异常业务类
     */
    public RespException(BodyBaseReq<?> req, BodyBaseResp<?> resp, BLogicException exInner) {
        super(exInner);
        this.req = req;
        this.exInner = exInner;
        // 设置响应信息
        this.resp = resp;
        this.resp.fillHeadByReq(this.req);
        this.resp.getHead().setResult(this.exInner.getCode());
        this.resp.getHead().setMsg(this.exInner.getMessage());
    }

    /**
     * 获取请求参数对象。<br>
     *
     * @return 请求参数对象
     */
    public BodyBaseReq<?> getReq() {
        return req;
    }

    /**
     * 获取默认响应对象。<br>
     *
     * @return 默认响应对象
     */
    public BodyBaseResp<?> getResp() {
        return resp;
    }

    /**
     * 获取内部异常信息。<br>
     *
     * @return 内部异常信息
     */
    public BLogicException getExInner() {
        return exInner;
    }
}
