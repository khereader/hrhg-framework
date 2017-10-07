/**
 * 分页及排序信息。<br>
 */
package com.integrity.framework.model;

import lombok.Data;

/**
 * 分页及排序信息。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
public class LimitOrder {
    /**
     * 开始位置
     */
    private int offset;
    /**
     * 记录数
     */
    private int size;
    /**
     * 排序字段
     */
    private String orderField;
    /**
     * 分组字段
     */
    private String groupField;
}