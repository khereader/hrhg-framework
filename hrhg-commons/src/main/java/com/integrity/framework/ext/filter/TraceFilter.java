/**
 * 跟踪过滤器。<br>
 */
package com.integrity.framework.ext.filter;

import com.integrity.framework.api.bean.BodyBaseResp;
import com.integrity.framework.api.code.FrameworkCode;
import com.integrity.framework.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/**
 * 跟踪过滤器。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Priority(Priorities.USER)
public abstract class TraceFilter implements ContainerRequestFilter, ContainerResponseFilter {
    /**
     * 请求头KEY--允许服务器HOST
     */
    public static final String HEADER_KEY_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    /**
     * 请求头VALUE--允许服务器HOST(任何主机)
     */
    public static final String HEADER_VALUE_ALLOW_ORIGIN = "*";
    /**
     * 请求头KEY--允许请求头信息
     */
    public static final String HEADER_KEY_ALLOW_HEADERS = "Access-Control-Allow-Headers";
    /**
     * 请求头VALUE--允许请求头信息
     */
    public static final String HEADER_VALUE_ALLOW_HEADERS = "Content-Type,x-requested-with,Authorization," +
            "Access-Control-Request-Origin,Last-Modified,If-Modified-Since,X-Token,ssi-token";
    /**
     * 请求头KEY--允许操作方法
     */
    public static final String HEADER_KEY_ALLOW_METHODS = "Access-Control-Allow-Methods";
    /**
     * 请求头VALUE--允许操作方法
     */
    public static final String HEADER_VALUE_ALLOW_METHODS = "POST, GET, PUT, DELETE, HEAD, TRACE, CONNECT, OPTIONS";
    /**
     * 请求头KEY--允许操作方法（OPTIONS）
     */
    public static final String HEADER_VALUE_OPTIONS = "OPTIONS";
    /**
     * 请求头KEY--最大缓存时间
     */
    public static final String HEADER_KEY_MAX_AGE = "Access-Control-Max-Age";
    /**
     * 请求头VALUE--最大缓存时间
     */
    public static final String HEADER_VALUE_MAX_AGE = "3600";

    /**
     * 日志输出对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TraceFilter.class);

    /**
     * 跟踪过滤器请求处理。<br>
     *
     * @param requestContext 请求参数上下文
     * @throws IOException IO异常
     */
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LogUtils.info(logger, FrameworkCode.Message.I_FILTER_TRACE_REQ.getFullCode(),
                FrameworkCode.Message.I_FILTER_TRACE_REQ.getMessage());
    }

    /**
     * 跟踪过滤器响应处理。<br>
     *
     * @param containerRequestContext  请求参数上下文
     * @param containerResponseContext 响应参数上下文
     * @throws IOException IO异常
     */
    public void filter(ContainerRequestContext containerRequestContext,
                       ContainerResponseContext containerResponseContext) throws IOException {
        LogUtils.info(logger, FrameworkCode.Message.I_FILTER_TRACE_RESP.getFullCode(),
                FrameworkCode.Message.I_FILTER_TRACE_RESP.getMessage());
        if (containerRequestContext.getMethod().equals(HEADER_VALUE_OPTIONS)) {
            // CORS跨域处理，响应预校验信息
            containerResponseContext.getHeaders().add(HEADER_KEY_ALLOW_ORIGIN, whiteHost());
            containerResponseContext.getHeaders().add(HEADER_KEY_ALLOW_HEADERS, HEADER_VALUE_ALLOW_HEADERS);
            containerResponseContext.getHeaders().add(HEADER_KEY_ALLOW_METHODS, HEADER_VALUE_ALLOW_METHODS);
            containerResponseContext.getHeaders().add(HEADER_KEY_MAX_AGE, maxAge());
        }

        // 获取body数据类型
        Class<?> clazz = containerResponseContext.getEntityClass();

        if (null != clazz && BodyBaseResp.class.isAssignableFrom(clazz)) {
            // 有响应数据时，添加响应头允许信息
            containerResponseContext.getHeaders().add(HEADER_KEY_ALLOW_ORIGIN, whiteHost());
        }
    }

    /**
     * 获取允许跨域白名单主机。<br>
     *
     * @return 允许跨域白名单主机
     */
    protected abstract String whiteHost();

    /**
     * 获取跨域验证最大缓存时间。<br>
     *
     * @return 跨域验证最大缓存时间
     */
    protected abstract String maxAge();
}