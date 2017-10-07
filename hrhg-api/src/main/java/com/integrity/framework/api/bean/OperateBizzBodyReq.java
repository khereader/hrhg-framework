/**
 * 业务操作控制请求消息体。<br>
 */
package com.integrity.framework.api.bean;

import com.integrity.framework.api.code.OpBizz;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 业务操作控制请求消息体。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OperateBizzBodyReq extends BodyReq {
    /**
     * 待操业务ID列表
     */
    private List<String> ids;
    /**
     * 动作类型
     * 根据type信息，定义动作值：
     * type为DELETE(1)时，act：1、删除；0、恢复（通常不会有此操作）
     * type为ENABLE(2)时，act：1、启用；0、禁用
     * type为SORT(3)时，act为排序值（1、2、3、4....）
     */
    private Integer act;
    /**
     * 业务类型（1、删除；2、启用／禁用；3、排序）
     */
    private OpBizz.Type type;
}
