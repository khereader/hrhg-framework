/**
 * 跟踪拦截器。<br>
 */
package com.integrity.framework.ext.filter;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.ext.ReaderInterceptorContext;

/**
 * 跟踪拦截器。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Priority(Priorities.USER)
public class DefaultTraceInterceptor extends TraceInterceptor {
    /**
     * 更新处理请求对象。<br>
     *
     * @param readerContext 请求处理上下文
     * @param objRead       请求对象
     * @return 更新后请求对象
     */
    @Override
    protected Object refreshReadObj(ReaderInterceptorContext readerContext, Object objRead) {
        return objRead;
    }
}
