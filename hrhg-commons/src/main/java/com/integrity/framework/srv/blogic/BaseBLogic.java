/**
 * 业务逻辑服务。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.exception.BLogicException;

/**
 * 业务逻辑服务。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface BaseBLogic<P extends Object, R extends Object> {
    /**
     * 业务执行接口。<br>
     *
     * @param param 业务处理逻辑参数
     * @return 业务处理结果
     * @throws BLogicException 业务逻辑异常
     */
    R execute(P param) throws BLogicException;

    /**
     * 获取结果参数类型。<br>
     *
     * @return 结果参数类型
     */
    Class<R> getClazzResult();
}
