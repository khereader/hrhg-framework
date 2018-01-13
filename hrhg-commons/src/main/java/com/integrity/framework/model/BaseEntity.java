/**
 * 数据基础模型。<br>
 */
package com.integrity.framework.model;

import lombok.Data;

import java.util.Date;

/**
 * 数据基础模型。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
public class BaseEntity {
    /**
     * 删除标记
     */
    protected Boolean deleteFlag;
    /**
     * 创建者
     */
    protected String creator;
    /**
     * 创建时间
     */
    protected Date createTime;
    /**
     * 更新者
     */
    protected String updator;
    /**
     * 更新时间
     */
    protected Date updateTime;
}