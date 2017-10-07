/**
 * 跟踪拦截器。<br>
 */
package com.integrity.framework.web.filter;

import com.alibaba.dubbo.rpc.RpcContext;
import com.integrity.framework.api.bean.BodyBaseReq;
import com.integrity.framework.api.bean.HeadReq;
import com.integrity.framework.ext.filter.TraceInterceptor;
import com.integrity.framework.utils.DataUtils;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Priorities;
import javax.ws.rs.ext.ReaderInterceptorContext;

/**
 * 跟踪拦截器。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Priority(Priorities.USER)
public class HrhgTraceInterceptor extends TraceInterceptor {
    /**
     * 更新处理请求对象。<br>
     *
     * @param readerContext 请求处理上下文
     * @param objRead       请求对象
     * @return 更新后请求对象
     */
    @Override
    protected Object refreshReadObj(ReaderInterceptorContext readerContext, Object objRead) {
        if (DataUtils.isNullOrEmpty(objRead)) {
            // 更新对象数据为空
            return objRead;
        }

        if (BodyBaseReq.class.isAssignableFrom(objRead.getClass())) {
            // 通用请求类型对象时
            // 获取Http请求对象
            HttpServletRequest servletRequest = RpcContext.getContext().getRequest(HttpServletRequest.class);
            // 获取请求URI
            String uri = servletRequest.getRequestURI();
            // 获取客户端IP
            String ip = getClientIp(servletRequest);
            // 更新请求信息
            HeadReq reqHead = (HeadReq) ((BodyBaseReq) objRead).getHead();
            // URI
            reqHead.getHttpRequestInfo().setUri(uri);
            // IP
            reqHead.getHttpRequestInfo().setIp(ip);
        }

        return objRead;
    }
}
