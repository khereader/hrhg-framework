/**
 * 简单Key-Value对象。<br>
 */
package com.hrhg.framework.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 简单Key-Value对象。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
@Data
public class KeyValueObj implements Serializable {
    /**
     * 关键字信息
     */
    private String key;
    /**
     * 值信息
     */
    private String value;
}
