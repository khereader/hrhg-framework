/**
 * 查询字段请求消息体。<br>
 */
package com.hrhg.framework.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 查询字段请求消息体。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SearchBodyReq extends SBodyReq {
    /**
     * 请求字段名
     */
    private List<String> fields;
}
