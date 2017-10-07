/**
 * 配置绑定跟踪拦截器。<br>
 */
package com.integrity.framework.ext.filter;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

/**
 * 配置绑定跟踪拦截器。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Priority(Priorities.USER)
public class DynamicTraceBinding implements DynamicFeature {
    /**
     * 配置绑定。<br>
     *
     * @param resourceInfo 绑定资源信息
     * @param context      上下文信息
     */
    public void configure(ResourceInfo resourceInfo, FeatureContext context) {
        context.register(DefaultTraceInterceptor.class);
    }
}