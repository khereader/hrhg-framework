/**
 * 远程服务错误基类。<br>
 */
package com.hrhg.framework.ext.emapper;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.hrhg.framework.bean.BodyBaseResp;
import com.hrhg.framework.code.SysCode;
import com.hrhg.framework.utils.JsonUtils;
import com.hrhg.framework.utils.LogUtils;
import com.hrhg.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * 远程服务错误基类。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class BaseExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {
    /**
     * 日志输出对象
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseExceptionMapper.class);

    /**
     * Map an exception to a {@link Response}. Returning
     * {@code null} results in a {@link Response.Status#NO_CONTENT}
     * response. Throwing a runtime exception results in a
     * {@link Response.Status#INTERNAL_SERVER_ERROR} response.
     *
     * @param exception the exception to map to a response.
     * @return a response mapped from the supplied exception.
     */
    @Override
    public Response toResponse(E exception) {
        // 生成响应参数
        BodyBaseResp<?> resp = makeResp(exception);
        // 日志输出
        LogUtils.error(logger, exception.getCause(), resp.getHead().getResult(), resp.getHead().getMsg());

        // 初始化响应结果
        String json = StringUtils.EMPTY_STRING;

        try {
            json = JsonUtils.object2Json(resp);
        } catch (Exception ex) {
            LogUtils.error(logger, ex,
                    SysCode.Message.E_SYS_EXCEPTION.getFullCode(), SysCode.Message.E_SYS_EXCEPTION.getMessage());
        }

        // 正常解析响应
        return Response.ok().entity(json).type(ContentType.APPLICATION_JSON_UTF_8).build();
    }

    /**
     * 生成异常响应消息。<br>
     *
     * @param ex 异常类型
     * @return 响应对象
     */
    protected abstract BodyBaseResp<?> makeResp(E ex);
}
