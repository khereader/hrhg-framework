/**
 * 清除缓存业务逻辑。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.api.bean.BodyReq;
import com.integrity.framework.api.bean.BodyResp;
import com.integrity.framework.api.code.CodePath;
import com.integrity.framework.redis.RedisDataSource;
import com.integrity.framework.utils.RedisUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 清除缓存业务逻辑。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class SimpleBaseCacheBLogic<P extends BodyReq, R extends BodyResp, T extends SimplePageBLogic<P, R>>
        extends AbstractBaseBLogic<P, R> implements SimpleWithAuthBLogic<P, R> {
    /**
     * 登录用户ID
     */
    protected String uidLogin;
    /**
     * Redis数据源
     */
    @Resource
    protected RedisDataSource redisDataSource;
    /**
     * 具体业务处理业务逻辑（按照类型注入）
     */
    @Autowired
    protected T bizzBLogic;

    /**
     * 获取缓存Key。<br>
     *
     * @return 缓存Key
     */
    protected abstract String cachekey();

    /**
     * 获取缓存key超时时间。<br>
     *
     * @return 超时时间(默认2个小时)
     */
    protected long expire() {
        return RedisUtils.DEFAULT_EXPIRE_HOUR_TWO;
    }

    /**
     * 更新鉴权信息。<br>
     *
     * @param uid      用户ID
     * @param codePath 编码路径
     */
    @Override
    public void refreshAuthInfo(String uid, CodePath codePath) {
        this.uidLogin = uid;
    }
}
