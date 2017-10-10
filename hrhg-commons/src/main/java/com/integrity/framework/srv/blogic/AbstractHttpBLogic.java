/**
 * HTTP访问业务逻辑服务。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.api.code.CodePath;
import com.integrity.framework.api.code.SysCode;
import com.integrity.framework.exception.BLogicException;
import com.integrity.framework.utils.DataUtils;

/**
 * HTTP访问业务逻辑服务。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class AbstractHttpBLogic<P extends Object, R extends Object>
        extends AbstractBaseBLogic<P, R> implements BaseBLogic<P, R> {
    /**
     * HTTP请求URL地址。<br>
     *
     * @param param 请求参数
     */
    protected abstract String reqURL(P param);

    /**
     * 预处理请求参数。<br>
     *
     * @param param 请求参数
     * @return 处理后请求参数
     * @throws Exception 系统异常
     */
    protected abstract P prePrecess(P param) throws Exception;

    /**
     * 使用Http工具。<br>
     *
     * @param param 请求参数
     * @return 响应结果
     * @Exception 系统异常
     */
    protected abstract R useHttpTools(P param) throws Exception;

    /**
     * 更新鉴权信息。<br>
     *
     * @param uid      用户ID
     * @param codePath 编码路径
     */
    @Override
    public void refreshAuthInfo(String uid, CodePath codePath) {
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
        if (DataUtils.isNullOrEmpty(param)) {
            // 请求参数为空的情况
            return null;
        }

        try {
            // 预处理参数
            prePrecess(param);

            // 执行Http请求
            return useHttpTools(param);
        } catch (Exception ex) {
            throw new BLogicException(ex, SysCode.Message.E_SYS_EXCEPTION);
        }
    }
}
