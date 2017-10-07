/**
 * 服务消息体基类响应参数。<br>
 */
package com.integrity.framework.api.bean;


import com.integrity.framework.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务消息体基类响应参数。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BodyBaseResp<B extends BodyResp> extends Response<HeadResp, B> {
    /**
     * 创建消息头。<br>
     */
    @Override
    protected void createHead() {
        this.head = new HeadResp();
    }

    /**
     * 从请求参数中填充部分请求参数。<br>
     *
     * @param req 请求参数
     * @param <R> 请求参数体类型
     */
    public <R extends BodyReq> void fillHeadByReq(BodyBaseReq<R> req) {
        fillHeadByReq(req, null);
    }

    /**
     * 从请求参数中填充部分请求参数。<br>
     *
     * @param req   请求参数
     * @param token 需要更新的token信息
     * @param <R>   请求体类型
     */
    public <R extends BodyReq> void fillHeadByReq(BodyBaseReq<R> req, String token) {
        if (null == req) {
            // 请求参数对象为空的情况
            return;
        }

        // 设置响应头信息
        this.head.setTime(req.getHead().getTime());
        this.head.setCode(req.getHead().getCode());
        this.head.setVer(req.getHead().getVer());
        this.head.setToken(StringUtils.isEmpty(token) ? req.getHead().getToken() : token);
    }
}
