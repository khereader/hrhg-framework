/**
 * 默认请求消息体。<br>
 */
package com.hrhg.framework.bean;

import com.hrhg.framework.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 默认请求消息体。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DefaultBodyResp extends BodyResp {
    /**
     * 当前时间戳
     */
    private Long now;
    /**
     * 默认响应字段数据
     */
    private Long resp;

    /**
     * 获取当前时间戳。<br>
     *
     * @return 当前时间戳
     */
    public String getNowText() {
        return DateUtils.normalDateText(this.now);
    }

    /**
     * 获取 默认响应字段数据。<br>
     *
     * @return 返回 默认响应字段数据
     */
    public String getRespText() {
        return DateUtils.normalDateText(this.resp);
    }
}
