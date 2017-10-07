/**
 * 事务处理业务逻辑服务。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.exception.BLogicException;

/**
 * 事务处理业务逻辑服务。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface TransactionBLogic<P extends Object, R extends Object> extends BaseBLogic<P, R> {
    /**
     * 业务执行接口。<br>
     *
     * @param param 业务处理逻辑参数
     * @return 业务处理结果
     * @throws BLogicException 业务逻辑异常
     */
    @Override
    R execute(P param) throws BLogicException;
}
