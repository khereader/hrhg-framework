/**
 * 获取缓存哈希业务逻辑。<br>
 */
package com.integrity.framework.srv.blogic;

import com.integrity.framework.api.bean.BodyReq;
import com.integrity.framework.api.bean.BodyResp;
import com.integrity.framework.api.code.FrameworkCode;
import com.integrity.framework.exception.BLogicException;
import com.integrity.framework.utils.DataUtils;
import com.integrity.framework.utils.RedisUtils;
import com.integrity.framework.utils.SignUtils;
import com.integrity.framework.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取缓存哈希业务逻辑。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class SimpleHashGetCacheBLogic<P extends BodyReq, R extends BodyResp, T extends SimplePageBLogic<P, R>>
        extends SimpleBaseCacheBLogic<P, R, T> {
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
}
