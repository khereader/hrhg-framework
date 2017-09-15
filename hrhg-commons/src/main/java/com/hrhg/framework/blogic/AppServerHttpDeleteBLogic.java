/**
 * 客户端-服务HTTP访问DELETE请求业务逻辑服务。<br>
 */
package com.hrhg.framework.blogic;

import com.hrhg.framework.utils.HttpUtils;

/**
 * 客户端-服务HTTP访问DELETE请求业务逻辑服务。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class AppServerHttpDeleteBLogic<P extends String, R extends Object> extends AbstractHttpDeleteBLogic<P, R> {
    /**
     * 预处理请求参数。<br>
     *
     * @param param 请求参数
     * @return 处理后请求参数
     * @throws Exception 系统异常
     */
    @Override
    protected P prePrecess(P param) throws Exception {
        return param;
    }

    /**
     * 使用Http工具。<br>
     *
     * @param param 请求参数
     * @return 响应结果
     * @Exception 系统异常
     */
    @Override
    protected R useHttpTools(P param) throws Exception {
        return HttpUtils.deleteJsonBean(reqURL(), param, this.clazzResult);
    }
}
