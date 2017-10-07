/**
 * 通用搜索条件信息对象。<br>
 */
package com.integrity.framework.api.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用搜索条件信息对象。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
public class BaseSearch implements Serializable {
    /**
     * 时间条件-开始
     */
    protected Long beginTime;
    /**
     * 时间条件-结束
     */
    protected Long endTime;
}
