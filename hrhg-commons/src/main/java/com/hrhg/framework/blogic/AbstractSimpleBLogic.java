/**
 * 简单业务逻辑服务。<br>
 */
package com.hrhg.framework.blogic;

import com.hrhg.framework.exception.BLogicException;

/**
 * 业务逻辑服务。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class AbstractSimpleBLogic<P extends Object, R extends Object>
        extends AbstractBLogic<P, R> implements BaseBLogic<P, R> {
    /**
     * 业务执行接口。<br>
     *
     * @param param 业务处理逻辑参数
     * @return 业务处理结果
     * @throws BLogicException 业务逻辑异常
     */
    @Override
    abstract public R execute(P param) throws BLogicException;
}
