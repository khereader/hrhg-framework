/**
 * 简单测试对象。
 */
package com.integrity.framework.api.pojo;

import com.integrity.framework.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 简单测试对象。
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Simple implements Serializable {
    /**
     * 测试ID
     */
    private String spId;
    /**
     * 测试名称
     */
    private String spName;
    /**
     * 测试时间
     */
    private Long time;
    /**
     * 操作列表
     */
    private List<OperateButton> operate;

    /**
     * 获取测试时间文案。<br>
     *
     * @return 测试时间文案
     */
    public String getTimeText() {
        return DateUtils.normalNullableDateText(this.time);
    }
}
