/**
 * 按钮操作状态对象。
 */
package com.integrity.framework.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 按钮操作状态对象。
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperateButton implements Serializable {
    /**
     * 操作名称
     */
    private String name;
    /**
     * 显示文案
     */
    private String text;
}
