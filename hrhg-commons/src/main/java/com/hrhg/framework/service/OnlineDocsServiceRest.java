/**
 * 在线文档REST服务。<br>
 */
package com.hrhg.framework.service;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * 在线文档REST服务。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface OnlineDocsServiceRest {
    /**
     * 获取监听Api文档的响应结果集。<br>
     *
     * @param app     系统应用
     * @param sc      servlet配置
     * @param headers 请求头信息
     * @param uriInfo 用户信息
     * @return Api文档的响应结果集
     */
    Response getListingJson(Application app, ServletConfig sc, HttpHeaders headers, UriInfo uriInfo);
}
