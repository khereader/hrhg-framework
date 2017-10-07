/**
 * 抽象业务基础逻辑服务。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.exception.BLogicException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 抽象业务基础逻辑服务。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class AbstractBaseBLogic<P extends Object, R extends Object> {
    /**
     * 参数类型
     */
    protected Class<P> clazzParam;
    /**
     * 结果类型
     */
    protected Class<R> clazzResult;

    /**
     * 默认构造函数。<br>
     */
    @SuppressWarnings("unchecked")
    public AbstractBaseBLogic() {
        // 获取父类类型
        Type genType = getClass().getGenericSuperclass();
        // 获取泛型参数
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        // 请求参数类型
        this.clazzParam = (Class) params[0];
        // 响应参数类型
        this.clazzResult = (Class) params[1];
    }

    /**
     * 获取请求参数类型。<br>
     *
     * @return 请求参数类型
     */
    public Class<P> getClazzParam() {
        return clazzParam;
    }

    /**
     * 获取结果参数类型。<br>
     *
     * @return 结果参数类型
     */
    public Class<R> getClazzResult() {
        return clazzResult;
    }

    /**
     * 业务执行接口。<br>
     *
     * @param param 业务处理逻辑参数
     * @return 业务处理结果
     * @throws BLogicException 业务逻辑异常
     */
    public abstract R execute(P param) throws BLogicException;
}
