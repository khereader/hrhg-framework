/**
 * 跟踪过滤器。<br>
 */
package com.hrhg.framework.ext.filter;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;

/**
 * 跟踪过滤器。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Priority(Priorities.USER)
public class DefaultTraceFilter extends TraceFilter {
    /**
     * 获取允许跨域白名单主机。<br>
     *
     * @return 允许跨域白名单主机
     */
    @Override
    protected String whiteHost() {
        return HEADER_VALUE_ALLOW_ORIGIN;
    }

    /**
     * 获取跨域验证最大缓存时间。<br>
     *
     * @return 跨域验证最大缓存时间
     */
    @Override
    protected String maxAge() {
        return HEADER_VALUE_MAX_AGE;
    }
}