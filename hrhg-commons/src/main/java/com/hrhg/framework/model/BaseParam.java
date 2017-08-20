/**
 * 通用搜索条件。<br>
 */
package com.hrhg.framework.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 通用搜索条件。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
public class BaseParam<M extends Object> {
    /**
     * 搜索创建时间开始
     */
    private Date beginTime;
    /**
     * 搜索创建时间结束
     */
    private Date endTime;
    /**
     * 待操作ID集合
     */
    private List<String> ids;
    /**
     * 分页排序信息
     */
    private LimitOrder lo;

    /**
     * 查询条件
     */
    private M sch;
    /**
     * 更新数据
     */
    private M udt;
    /**
     * 检查字段是否为空
     */
    private M chk;
    /**
     * 系统时间
     */
    private Date systemDate;
}