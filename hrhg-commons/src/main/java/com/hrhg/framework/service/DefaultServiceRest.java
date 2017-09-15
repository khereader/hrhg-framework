/**
 * REST默认服务。<br>
 */
package com.hrhg.framework.service;

import com.hrhg.framework.bean.DefaultReq;
import com.hrhg.framework.bean.DefaultResp;
import com.hrhg.framework.bean.SearchReq;
import com.hrhg.framework.bean.SearchResp;
import com.hrhg.framework.bean.SelfReq;
import com.hrhg.framework.bean.SelfResp;
import com.hrhg.framework.exception.RespException;

/**
 * REST默认服务。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface DefaultServiceRest {
    /**
     * 简单数据类型接口。<br>
     *
     * @param req 请求参数
     * @return 简单响应结果
     */
    String simple(String req);

    /**
     * 自定义数据类型接口。<br>
     *
     * @param req 请求参数
     * @return 自定义响应结果
     */
    SelfResp self(SelfReq req);

    /**
     * 默认测试接口。<br>
     *
     * @param req 请求参数
     * @return 响应结果
     * @throws RespException 业务异常
     */
    DefaultResp test(DefaultReq req);

    /**
     * 查询字段接口。<br>
     *
     * @param req 请求参数
     * @return 查询字段响应结果
     */
    SearchResp search(SearchReq req);
}
