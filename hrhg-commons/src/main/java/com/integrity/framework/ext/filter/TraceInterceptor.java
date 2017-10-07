/**
 * 跟踪拦截器。<br>
 */
package com.integrity.framework.ext.filter;

import com.integrity.framework.api.code.SysCode;
import com.integrity.framework.utils.LogUtils;
import com.integrity.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import java.io.IOException;

/**
 * 跟踪拦截器。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Priority(Priorities.USER)
public abstract class TraceInterceptor implements ReaderInterceptor, WriterInterceptor {
    /**
     * 请求头VALUE--前置IP
     */
    public static final String HEADER_VALUE_FORWARDED_FOR = "X-Forwarded-For";
    /**
     * 请求头VALUE--实际IP
     */
    public static final String HEADER_VALUE_REAL_IP = "X-Real-IP";
    /**
     * 请求头VALUE--未知IP
     */
    public static final String HEADER_VALUE_UNKNOWN_IP = "unKnown";

    /**
     * 日志输出对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TraceInterceptor.class);

    /**
     * 从请求参数读取信息到对象。<br>
     *
     * @param readerInterceptorContext 读取拦截器上下问
     * @return 请求参数对象
     * @throws IOException             IO异常
     * @throws WebApplicationException 系统应用异常
     */
    public Object aroundReadFrom(ReaderInterceptorContext readerInterceptorContext)
            throws IOException, WebApplicationException {
        LogUtils.info(logger, SysCode.Message.I_FILTER_INTERCEPTOR_REQ.getFullCode(),
                SysCode.Message.I_FILTER_INTERCEPTOR_REQ.getMessage());
        // 处理生成请求对象
        Object objRead = readerInterceptorContext.proceed();
        // 更新处理请求对象
        return refreshReadObj(readerInterceptorContext, objRead);
    }

    /**
     * 更新处理请求对象。<br>
     *
     * @param readerContext 请求处理上下文
     * @param objRead       请求对象
     * @return 更新后请求对象
     */
    protected abstract Object refreshReadObj(ReaderInterceptorContext readerContext, Object objRead);

    /**
     * 输出对象到响应参数。<br>
     *
     * @param writerInterceptorContext 响应输出上下文
     * @throws IOException             IO异常
     * @throws WebApplicationException 系统应用异常
     */
    public void aroundWriteTo(WriterInterceptorContext writerInterceptorContext)
            throws IOException, WebApplicationException {
        LogUtils.info(logger, SysCode.Message.I_FILTER_INTERCEPTOR_RESP.getFullCode(),
                SysCode.Message.I_FILTER_INTERCEPTOR_RESP.getMessage());
        writerInterceptorContext.proceed();
    }

    /**
     * 获取客户端IP。<br>
     *
     * @return 客户端IP
     */
    protected String getClientIp(HttpServletRequest servletRequest) {
        // 获取代理IP
        String ip = servletRequest.getHeader(HEADER_VALUE_FORWARDED_FOR);

        if (StringUtils.isNotEmpty(ip) && !HEADER_VALUE_UNKNOWN_IP.equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            // 获取逗号分隔字符串
            return StringUtils.split(ip, StringUtils.COMMA_STRING)[0];
        }

        // 获取真实IP
        ip = servletRequest.getHeader(HEADER_VALUE_REAL_IP);
        if (StringUtils.isNotEmpty(ip) && !HEADER_VALUE_UNKNOWN_IP.equalsIgnoreCase(ip)) {
            return ip;
        }

        // 返回远程IP
        return servletRequest.getRemoteAddr();
    }
}
