/**
 * 获取缓存业务逻辑。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.api.bean.BodyReq;
import com.integrity.framework.api.bean.BodyResp;
import com.integrity.framework.api.code.CodePath;
import com.integrity.framework.api.code.FrameworkCode;
import com.integrity.framework.exception.BLogicException;
import com.integrity.framework.redis.RedisDataSource;
import com.integrity.framework.utils.DataUtils;
import com.integrity.framework.utils.RedisUtils;
import com.integrity.framework.utils.SignUtils;
import com.integrity.framework.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 获取缓存业务逻辑。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class SimpleGetCacheBLogic<P extends BodyReq, R extends BodyResp, T extends SimplePageBLogic<P, R>>
        extends AbstractBaseBLogic<P, R> implements SimpleWithAuthBLogic<P, R> {
    /**
     * 登录用户ID
     */
    protected String uidLogin;
    /**
     * Redis数据源
     */
    @Resource
    private RedisDataSource redisDataSource;
    /**
     * 具体业务处理业务逻辑（按照类型注入）
     */
    @Autowired
    protected T bizzBLogic;

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

        // 缓存中Key参数
        String redisKey = cachekey();

        if (StringUtils.isEmpty(redisKey)) {
            // 缓存Key信息为空
            throw new BLogicException(FrameworkCode.Message.E_CACH_KEY);
        }

        // 检查条件
        String field;

        try {
            field = SignUtils.makeBeanSign(param, redisKey);
        } catch (Exception e) {
            // 获取字段异常时
            throw new BLogicException(e, FrameworkCode.Message.E_CACH_FEILD);
        }

        // 系统参数响应参数
        R resp;

        try {
            // 获取缓存中的配置参数
            resp = RedisUtils.hgetJson(redisDataSource, redisKey, field, this.getClazzResult());
        } catch (Exception e) {
            throw new BLogicException(e, FrameworkCode.Message.E_SYS_EXCEPTION);
        }

        if (!DataUtils.isNullOrEmpty(resp)) {
            // 存在缓存响应结果
            return resp;
        }

        // 缓存中的配置参数不存在
        // 访问者信息
        this.bizzBLogic.setUidLogin(this.uidLogin);
        // 持久层获取文章详细内容
        resp = this.bizzBLogic.execute(param);

        try {
            // 缓存中 设置系统参数信息
            RedisUtils.hsetJson(redisDataSource, redisKey, field, resp, expire());
        } catch (Exception e) {
            throw new BLogicException(e, FrameworkCode.Message.E_SYS_EXCEPTION);
        }

        return resp;
    }

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
