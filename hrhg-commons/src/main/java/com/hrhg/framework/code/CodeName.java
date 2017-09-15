/**
 * 编码名称类型接口。<br>
 */
package com.hrhg.framework.code;

/**
 * 编码名称类型接口。<br>
 *
 * @author 李海军
 * @since 1.0.0
 */
public interface CodeName extends CheckType {
    /**
     * 获取编码。<br>
     *
     * @return 编码
     */
    int getCode();

    /**
     * 获取编码名称。<br>
     *
     * @return 全编码
     */
    String getCodeName();
}
