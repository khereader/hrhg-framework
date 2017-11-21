/**
 * 面向切面抽象业务逻辑服务。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.api.bean.BodyBaseReq;
import com.integrity.framework.api.bean.BodyBaseResp;
import com.integrity.framework.api.code.FrameworkCode;
import com.integrity.framework.exception.BLogicException;
import com.integrity.framework.utils.ClazzUtils;

/**
 * 面向切面抽象业务逻辑服务。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class AbstractAopBLogic<P extends Object, R extends Object>
        extends AbstractBLogic<P, R> implements TransactionBLogic<P, R> {
    /**
     * 预处理逻辑。<br>
     *
     * @param param 预处理参数
     * @return 预处理结果
     * @throws BLogicException 业务逻辑异常
     */
    protected P preLogic(P param) throws BLogicException {
        return param;
    }

    /**
     * 业务逻辑处理。<br>
     *
     * @param req  请求参数
     * @param resp 响应参数
     * @return 业务处理结果
     * @throws BLogicException 业务逻辑异常
     */
    protected abstract R doExecute(P req, R resp) throws BLogicException;

    /**
     * 后处理逻辑。<br>
     *
     * @param req  请求参数
     * @param resp 处理结果
     * @return 处理结果修正
     * @throws BLogicException 业务逻辑异常
     */
    protected R postLogic(P req, R resp) throws BLogicException {
        return resp;
    }

    /**
     * 业务执行接口。<br>
     *
     * @param param 业务处理逻辑参数
     * @return 业务处理结果
     * @throws BLogicException 业务逻辑异常
     */
    @Override
    public R execute(P param) throws BLogicException {
        try {
            // 业务预处理逻辑
            P req = preLogic(param);
            // 创建响应参数
            R resp = ClazzUtils.create(this.clazzResult);

            if (BodyBaseResp.class.isAssignableFrom(this.clazzResult) &&
                    BodyBaseReq.class.isAssignableFrom(this.clazzParam)) {
                // 正常业务请求／响应业务逻辑处理
                // 填充消息头参数
                ((BodyBaseResp<?>) resp).fillHeadByReq((BodyBaseReq<?>) req);
            }

            // 业务逻辑处理
            R result = doExecute(req, resp);
            // 业务后处理逻辑
            return postLogic(req, result);
        } catch (BLogicException bex) {
            // 业务逻辑异常
            throw bex;
        } catch (Exception ex) {
            // 系统异常
            throw new BLogicException(ex, FrameworkCode.Message.E_SYS_EXCEPTION.getFullCode(),
                    FrameworkCode.Message.E_SYS_EXCEPTION.getMessage());
        }
    }
}
