/**
 * 基础通用Srv服务功能。<br>
 */
package com.integrity.framework.srv.rpc;

import com.integrity.framework.api.bean.BodyBaseReq;
import com.integrity.framework.api.bean.BodyBaseResp;
import com.integrity.framework.api.bean.HeadReq;
import com.integrity.framework.api.bean.HeadResp;
import com.integrity.framework.api.bean.OperateBizzReq;
import com.integrity.framework.api.bean.OperateBizzResp;
import com.integrity.framework.api.bean.SsoReq;
import com.integrity.framework.api.bean.SsoResp;
import com.integrity.framework.api.code.ApiType;
import com.integrity.framework.api.code.CodePath;
import com.integrity.framework.api.code.FrameworkCode;
import com.integrity.framework.api.code.OpBizz;
import com.integrity.framework.exception.BLogicException;
import com.integrity.framework.exception.RespException;
import com.integrity.framework.service.SsoService;
import com.integrity.framework.srv.blogic.BaseBLogic;
import com.integrity.framework.utils.BeanUtils;
import com.integrity.framework.utils.ClazzUtils;
import com.integrity.framework.utils.DataUtils;
import com.integrity.framework.utils.LogUtils;
import com.integrity.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 基础通用Srv服务功能。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public abstract class BaseServiceImpl {
    /**
     * 日志输出对象
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    /**
     * 消息头忽略复制字段名
     */
    private static final String HEAD_FIELD_IGONE = "httpRequestInfo";

    /**
     * 当前业务服务业务编码/URI集合。<br>
     *
     * @return 业务编码/URI集合
     */
    protected abstract Map<String, CodePath> bizzCodePath();

    /**
     * 获取无需权限检查业务编码/URI集合。<br>
     *
     * @return 无需检查权限业务编码/URI集合
     */
    protected abstract Map<String, CodePath> uncheckBizzCode();

    /**
     * 获取统一鉴权服务。<br>
     *
     * @param req 更新请求参数
     * @return 统一鉴权服务
     */
    protected abstract SsoService ssoService(SsoReq req);

    /**
     * 删除业务处理。<br>
     *
     * @param blogic 业务逻辑对象
     * @param req    请求参数
     * @return 响应参数
     * @throws RespException 业务逻辑异常
     */
    @SuppressWarnings("unchecked")
    protected OperateBizzResp del(BaseBLogic blogic, OperateBizzReq req) throws RespException {
        // 删除
        req.getBody().setType(OpBizz.Type.DELETE);
        // 设置动作
        req.getBody().setAct(1);
        return process(blogic, req);
    }

    /**
     * 启用／禁用业务处理。<br>
     *
     * @param blogic 业务逻辑对象
     * @param req    请求参数
     * @return 响应参数
     * @throws RespException 业务逻辑异常
     */
    @SuppressWarnings("unchecked")
    protected OperateBizzResp enable(BaseBLogic blogic, OperateBizzReq req) throws RespException {
        // 启用／禁用
        req.getBody().setType(OpBizz.Type.STATUS);
        return process(blogic, req);
    }

    /**
     * 置顶排序业务处理。<br>
     *
     * @param blogic 业务逻辑对象
     * @param req    请求参数
     * @return 响应参数
     * @throws RespException 业务逻辑异常
     */
    @SuppressWarnings("unchecked")
    protected OperateBizzResp sort(BaseBLogic blogic, OperateBizzReq req) throws RespException {
        // 排序
        req.getBody().setType(OpBizz.Type.SORT);
        return process(blogic, req);
    }

    /**
     * 置顶排序业务处理。<br>
     *
     * @param blogic 业务逻辑对象
     * @param req    请求参数
     * @return 响应参数
     * @throws RespException 业务逻辑异常
     */
    @SuppressWarnings("unchecked")
    protected <P extends BodyBaseReq, R extends BodyBaseResp> R sso(BaseBLogic blogic, P req) throws RespException {
        return process(blogic, req, true);
    }

    /**
     * 业务处理。<br>
     *
     * @param blogic 业务逻辑对象
     * @param req    请求参数
     * @return 响应参数
     * @throws RespException 业务逻辑异常
     */
    @SuppressWarnings("unchecked")
    protected <P extends BodyBaseReq, R extends BodyBaseResp> R process(BaseBLogic blogic, P req) throws RespException {
        return process(blogic, req, false);
    }

    /**
     * 业务处理。<br>
     *
     * @param blogic  业务逻辑对象
     * @param req     请求参数
     * @param ssoFlag 是否进行鉴权
     * @return 响应参数
     * @throws RespException 业务逻辑异常
     */
    @SuppressWarnings("unchecked")
    protected <P extends BodyBaseReq, R extends BodyBaseResp> R process(
            BaseBLogic blogic, P req, boolean ssoFlag) throws RespException {
        try {
            // 更新请求头信息
            CodePath codePath = refreshReqCode((HeadReq) req.getHead());

            // 登陆用户ID
            String uidLogin = StringUtils.NULL_STRING;

            if (!ssoFlag) {
                // 进行鉴权的情况
                uidLogin = checkAuthAndRefreshToken((HeadReq) req.getHead());
            }

            // 更新业务鉴权信息
            blogic.refreshAuthInfo(uidLogin, codePath);

            // 业务逻辑处理
            R resp = (R) blogic.execute(req);
            // 获取响应头
            HeadResp head = (HeadResp) resp.getHead();
            // 输出日志
            LogUtils.info(logger, head.getResult(), head.getMsg());
            return resp;
        } catch (BLogicException bex) {
            // 输出日志
            LogUtils.error(logger, bex, bex.getCode(), bex.getMessage());
            // 响应结果对象
            // 实例化响应结果对象
            R resp = (R) ClazzUtils.create(blogic.getClazzResult());
            // 业务逻辑异常转换为响应异常
            // throw new RespException(req, resp, bex);
            RespException respException = new RespException(req, resp, bex);
            // 返回异常结果
            return (R) respException.getResp();
        }
    }

    /**
     * 更新请求编码信息。<br>
     *
     * @param reqHead 请求头信息
     * @return CodePath对象
     * @throws BLogicException 业务逻辑异常
     */
    private CodePath refreshReqCode(HeadReq reqHead) throws BLogicException {
        if (DataUtils.isNullOrEmpty(reqHead)) {
            // 请求头为空时
            throw new BLogicException(FrameworkCode.Message.E_PARAM_FORMAT);
        }

        // 获取编码CodePath集合
        Map<String, CodePath> codePathMap = bizzCodePath();

        if (StringUtils.isNotEmpty(reqHead.getHttpRequestInfo().getUri())) {
            // 根据请求URI，获取业务编码
            String code = findCodeByUri(codePathMap, reqHead.getHttpRequestInfo().getUri());

            if (StringUtils.isEmpty(code)) {
                // 业务编码为空
                throw new BLogicException(FrameworkCode.Message.E_NOT_FOUND);
            }

            // 强制更新业务编码
            reqHead.setCode(code);
        }

        // 返回业务编码对应的CodePath对象
        return codePathMap.get(reqHead.getCode());
    }

    /**
     * 根据uri，获取对应的业务编码。<br>
     *
     * @param codePathMap 业务编码集合
     * @param uri         访问请求URI
     * @return 业务编码
     */
    private String findCodeByUri(Map<String, CodePath> codePathMap, String uri) {
        if (DataUtils.isNullOrEmpty(codePathMap)) {
            // 编码/URI集合为空时
            return null;
        }

        if (DataUtils.isNullOrEmpty(uri)) {
            // URI集合为空时
            return null;
        }

        for (Map.Entry<String, CodePath> codePath : codePathMap.entrySet()) {
            // 逐个遍历编码／逻辑对象
            if (StringUtils.isNotEmptyEquals(uri, codePath.getValue().getFullPath())) {
                // URI和编码路径相同
                return codePath.getKey();
            }
        }

        return null;
    }

    /**
     * 检查用户权限，同时更新用户token信息。<br>
     *
     * @param reqHead 请求头信息
     * @return 用户ID
     * @throws BLogicException 业务逻辑异常
     */
    private String checkAuthAndRefreshToken(HeadReq reqHead) throws BLogicException, RespException {
        if (DataUtils.isNullOrEmpty(reqHead)) {
            // 请求头为空时
            throw new BLogicException(FrameworkCode.Message.E_PARAM_FORMAT);
        }

        // 获取无需检查权限业务编码
        Map<String, CodePath> uncheckBizzCodeMap = uncheckBizzCode();
        // 需要鉴权标记(true:需要鉴权；false:不需要鉴权)
        boolean checkAuthFlag = DataUtils.isNullOrEmpty(uncheckBizzCodeMap)
                || !uncheckBizzCodeMap.keySet().contains(reqHead.getCode());

        // 获取应用类型
        ApiType.Type appType = ApiType.Type.fromCode(reqHead.getAppId());

        if (null == appType) {
            // 无效的应用编码类型
            throw new BLogicException(FrameworkCode.Message.E_NOT_EXIST_APPTYPE);
        }

        switch (appType) {
            case APP: {
                // 应用平台API服务鉴权
                break;
            }
            case MIS: {
                // 后台MIS用户鉴权
                break;
            }
            case BATCH: {
                // 内部批处理应用服务
                // TODO 增加token鉴权
                return ApiType.Type.BATCH.name();
            }
            case OP_ACTIVE: {
                // 活动页应用服务
                // TODO 增加token鉴权
                return ApiType.Type.OP_ACTIVE.name();
            }
            case OPEN: {
                // 开放平台接口服务
                // TODO 增加token鉴权
                return ApiType.Type.OPEN.name();
            }
            case CALLBACK: {
                // 供应商回调接口服务
                // TODO 增加token鉴权
                return ApiType.Type.CALLBACK.name();
            }
            default: {
                // 无效的应用编码类型
                throw new BLogicException(FrameworkCode.Message.E_NOT_EXIST_APPTYPE);
            }
        }

        // 请求参数
        SsoReq req = new SsoReq();

        try {
            // 复制原请求属性(忽略)
            BeanUtils.copyBeanValue(reqHead, req.getHead(), false, true, HEAD_FIELD_IGONE);
        } catch (Exception e) {
            throw new BLogicException(FrameworkCode.Message.E_NO_PROPERTY);
        }

        // 获取鉴权服务
        SsoService ssoService = ssoService(req);

        if (null == ssoService) {
            // 鉴权服务对象为空
            throw new BLogicException(FrameworkCode.Message.E_NO_AUTH_SERVICE);
        }

        try {
            // 鉴权服务
            SsoResp resp = ssoService.sso(req);

            if (DataUtils.isNullOrEmpty(resp)) {
                if (checkAuthFlag) {
                    // 需要鉴权
                    // 用户鉴权失败
                    throw new BLogicException(FrameworkCode.Message.E_SYS_EXCEPTION);
                } else {
                    // 无需鉴权
                    return null;
                }
            }

            if (!FrameworkCode.Message.OK.getCode().equals(resp.getHead().getResult())) {
                if (checkAuthFlag) {
                    // 需要鉴权
                    // 鉴权逻辑失败
                    throw new BLogicException(resp.getHead().getResult(), resp.getHead().getMsg());
                } else {
                    // 无需鉴权
                    return null;
                }
            }

            if (StringUtils.isEmpty(resp.getHead().getToken())) {
                if (checkAuthFlag) {
                    // 需要鉴权
                    // 用户鉴权失败
                    throw new BLogicException(FrameworkCode.Message.E_AUTH_USER);
                } else {
                    // 无需鉴权
                    return null;
                }
            }

            // 更新用户token
            reqHead.setToken(resp.getHead().getToken());
            // 返回用户ID
            return resp.getBody().getUid();
        } catch (RespException respEx) {
            if (checkAuthFlag) {
                // 需要鉴权
                throw respEx;
            } else {
                // 无需鉴权
                return null;
            }
        }
    }
}
