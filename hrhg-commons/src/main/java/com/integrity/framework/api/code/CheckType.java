/**
 * 检查枚举类型接口。<br>
 */
package com.integrity.framework.api.code;

/**
 * 检查枚举类型接口。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface CheckType {

    /**
     * 是否为当前类型。<br>
     *
     * @param code 枚举类型编码
     * @return true：是当前类型；false：不是当前类型
     */
    boolean isThisType(Integer code);
}
