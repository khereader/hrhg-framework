/**
 * 获取缓存业务逻辑。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.api.code.CodePath;
import com.integrity.framework.api.code.FrameworkCode;
import com.integrity.framework.exception.BLogicException;
import com.integrity.framework.redis.RedisDataSource;
import com.integrity.framework.utils.DataUtils;
import com.integrity.framework.utils.RedisUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.annotation.Resource;

/**
 * 清除缓存业务逻辑。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class SimpleClearCacheBLogic<P extends Object, R extends Object>
        extends AbstractBaseBLogic<P, R> implements BaseBLogic<P, R> {
    /**
     * Redis数据源
     */
    @Resource
    private RedisDataSource redisDataSource;

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
            // 请求参数为空
            throw new BLogicException(FrameworkCode.Message.E_PARAM_NULL);
        }

        try {
            // 删除受影响缓存信息
            RedisUtils.del(redisDataSource, cachekeys());
        } catch (Exception e) {
            throw new BLogicException(e, FrameworkCode.Message.E_SYS_EXCEPTION);
        }

        return null;
    }

    /**
     * 获取缓存Key。<br>
     *
     * @return 缓存Key
     */
    protected abstract String[] cachekeys();

    /**
     * 更新鉴权信息。<br>
     *
     * @param uid      用户ID
     * @param codePath 编码路径
     */
    @Override
    public void refreshAuthInfo(String uid, CodePath codePath) {
    }
}
