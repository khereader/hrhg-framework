/**
 * 数据基础模型。<br>
 */
package com.hrhg.framework.model;

import java.util.Date;

/**
 * 数据基础模型。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public class BaseEntity {
    /**
     * 删除标记
     */
    private Boolean deleteFlag;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updator;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 获取删除标记。<br>
     *
     * @return 删除标记
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置删除标记。<br>
     *
     * @param deleteFlag 删除标记
     */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * 获取创建者。<br>
     *
     * @return 创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建者。<br>
     *
     * @param creator 创建者
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 获取创建时间。<br>
     *
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间。<br>
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新者。<br>
     *
     * @return 更新者
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * 设置更新者。<br>
     *
     * @param updator 更新者
     */
    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    /**
     * 获取更新时间。<br>
     *
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     *  设置更新时间。<br>
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}