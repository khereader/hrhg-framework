/**
 * 响应消息头。<br>
 */
package com.hrhg.framework.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 响应消息头。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HeadResp extends Head {
    /**
     * 业务处理结果
     */
    @NotNull
    private String result;
    /**
     * 业务处理结果描述详细信息
     */
    @NotNull
    private String msg;
}
