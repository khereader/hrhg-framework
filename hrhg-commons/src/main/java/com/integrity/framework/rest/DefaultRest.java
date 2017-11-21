/**
 * 默认Rest服务。<br>
 */
package com.integrity.framework.rest;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.integrity.framework.api.bean.DefaultReq;
import com.integrity.framework.api.bean.DefaultResp;
import com.integrity.framework.api.bean.SearchReq;
import com.integrity.framework.api.bean.SearchResp;
import com.integrity.framework.api.bean.SelfReq;
import com.integrity.framework.api.bean.SelfResp;
import com.integrity.framework.api.code.FrameworkCode;
import com.integrity.framework.exception.RespException;
import com.integrity.framework.service.DefaultService;
import com.integrity.framework.service.DefaultServiceRest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 默认Rest服务。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Path(FrameworkCode.BASE_PATH_FRAMEWORK)
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Service
public class DefaultRest implements DefaultServiceRest {
    /**
     * 默认测试Srv服务
     */
    @Reference
    private DefaultService defaultService;

    /**
     * 简单数据类型接口。<br>
     *
     * @param req 请求参数
     * @return 简单响应结果
     */
    @POST
    @Path(FrameworkCode.PATH_SIMPLE)
    public String simple(String req) {
        return this.defaultService.simple(req);
    }

    /**
     * 自定义数据类型接口。<br>
     *
     * @param req 请求参数
     * @return 自定义响应结果
     */
    @POST
    @Path(FrameworkCode.PATH_SELF)
    @Consumes({MediaType.TEXT_XML})
    @Produces({ContentType.TEXT_XML_UTF_8})
    public SelfResp self(SelfReq req) {
        return this.defaultService.self(req);
    }

    /**
     * 默认测试接口。<br>
     *
     * @param req 请求参数
     * @return 响应结果
     * @throws RespException 业务异常
     */
    @POST
    @Path(FrameworkCode.PATH_TEST)
    public DefaultResp test(DefaultReq req) {
        return this.defaultService.test(req);
    }

    /**
     * 查询字段接口。<br>
     *
     * @param req 请求参数
     * @return 查询字段响应结果
     */
    @POST
    @Path(FrameworkCode.PATH_SEARCH)
    public SearchResp search(SearchReq req) {
        return this.defaultService.search(req);
    }
}
