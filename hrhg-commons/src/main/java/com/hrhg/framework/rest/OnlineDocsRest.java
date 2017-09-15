/**
 * 在线文档REST服务。<br>
 */
package com.hrhg.framework.rest;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.hrhg.framework.code.DocsCode;
import com.hrhg.framework.code.SysRoot;
import com.hrhg.framework.ext.filter.TraceFilter;
import com.hrhg.framework.service.OnlineDocsServiceRest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.jaxrs.listing.BaseApiListingResource;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * 在线文档REST服务。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Path(SysRoot.PATH_DOCS)
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
@Service
public class OnlineDocsRest extends BaseApiListingResource implements OnlineDocsServiceRest {
    /**
     * Servlet上下文
     */
    @Context
    private ServletContext context;

    /**
     * 获取监听Api文档的响应结果集。<br>
     *
     * @param app     系统应用
     * @param sc      servlet配置
     * @param headers 请求头信息
     * @param uriInfo 用户信息
     * @return Api文档的响应结果集
     */
    @GET
    @Path(DocsCode.PATH_SWAGGER)
    @Override
    public Response getListingJson(@Context Application app, @Context ServletConfig sc,
                                   @Context HttpHeaders headers, @Context UriInfo uriInfo) {
        try {
            // 获取swagger响应结果
            Response resp = getListingJsonResponse(app, context, sc, headers, uriInfo);
            // 设置跨域信息
            resp.getHeaders().add(TraceFilter.HEADER_KEY_ALLOW_ORIGIN, TraceFilter.HEADER_VALUE_ALLOW_ORIGIN);
            resp.getHeaders().add(TraceFilter.HEADER_KEY_ALLOW_HEADERS, TraceFilter.HEADER_VALUE_ALLOW_HEADERS);
            resp.getHeaders().add(TraceFilter.HEADER_KEY_ALLOW_METHODS, TraceFilter.HEADER_VALUE_ALLOW_METHODS);
            resp.getHeaders().add(TraceFilter.HEADER_KEY_MAX_AGE, TraceFilter.HEADER_VALUE_MAX_AGE);

            return resp;
        } catch (JsonProcessingException e) {
            return Response.status(404).build();
        }
    }
}
